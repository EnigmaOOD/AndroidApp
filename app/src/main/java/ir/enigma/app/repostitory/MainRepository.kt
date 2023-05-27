package ir.enigma.app.repostitory

import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Group
import ir.enigma.app.model.Purchase
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.network.AddUserToGroupRequest
import ir.enigma.app.network.Api
import ir.enigma.app.ui.group.*
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: Api) {

    companion object {
        const val NO_GROUP = "NoGroup"
    }

    suspend fun getGroupWithMembers(token: String, groupId: Int): ApiResult<Group> {
        val result = handleException({ api.getAGroup(token, groupId) }) {
            null
        }
        result.data?.members = handleException({ api.getGroupMembers(token, groupId) }) {
            null
        }.data

        MyLog.log(
            structureLayer = StructureLayer.Repository,
            className = "MainRepository",
            methodName = "getGroupWithMembers",
            type = LogType.Info,
            message = "members of group with id $groupId : ${result.data?.members}",
        )
        return result
    }

    suspend fun getGroups(token: String): ApiResult<Flow<List<Group>>> {
        val result = handleException({ api.getGroups(token) }) {
            if (it == 404)
                NO_GROUP
            else
                null
        }

        return when (result) {
            is ApiResult.Success -> {
                MyLog.log(
                    structureLayer = StructureLayer.Repository,
                    className = "MainRepository",
                    methodName = "getGroups",
                    type = LogType.Info,
                    message = "group list: ${result.data?.groups}",
                )
                ApiResult.Success(flow {
                    emit(result.data!!.groups)
                })
            }
            else -> {
                if (result.message == NO_GROUP) {
                    MyLog.log(
                        structureLayer = StructureLayer.Repository,
                        className = "MainRepository",
                        methodName = "getGroups",
                        type = LogType.Info,
                        message = "group list is Empty",
                    )
                    ApiResult.Success(_data = flowOf(listOf()))
                } else {
                    MyLog.log(
                        structureLayer = StructureLayer.Repository,
                        className = "MainRepository",
                        methodName = "getGroups",
                        type = LogType.Error,
                        message = "group list error:  ${result.message}",
                    )
                    ApiResult.Error(result.message ?: "خطا در دریافت گروه ها")
                }
            }
        }
    }

    suspend fun getGroupPurchases(
        token: String,
        groupId: Int,
        filter: Int = 0
    ): ApiResult<Flow<List<Purchase>>> {
        val result = try {
            when (filter) {
                FILTER_OLDEST, FILTER_NEWEST -> api.getGroupPurchases(token, groupId).body()
                FILTER_MOST_EXPENSIVE, FILTER_CHEAPEST -> api.filterBaseDecrease(token, groupId)
                    .body()
                FILTER_YOUR_PURCHASES -> api.filterByMe(token, groupId).body()!!.buyer_buys
                else -> api.getGroupPurchases(token, groupId).body()

            }
        } catch (e: Exception) {
            MyLog.log(
                structureLayer = StructureLayer.Network,
                className = "MainRepository",
                methodName = "getGroupPurchases",
                type = LogType.Error,
                message = "group purchases Exception:",
                exception = e
            )
            null
        }

        return if (result != null) {
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "getGroupPurchases",
                type = LogType.Info,
                message = "group with id:$groupId and filter: $filter purchases: ${result}}",
            )
            ApiResult.Success(flow {
                emit(result)
            })
        } else {
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "getGroupPurchases",
                type = LogType.Error,
                message = "group with id:$groupId and filter: $filter purchases error",
            )
            ApiResult.Error("خطا در دریافت خرید ها")
        }

    }

    suspend fun createPurchase(
        token: String,
        groupId: Int,
        purchase: Purchase
    ): ApiResult<Any> {
        val obj = CreatePurchaseRequest(
            groupID = groupId,
            cost = purchase.totalPrice,
            description = purchase.title,
            date = purchase.date,
            picture_id = purchase.purchaseCategoryIndex,
            added_by = purchase.sender.id,
            buyers = purchase.buyers.map { ContributionRequest(it.user.id, it.price) },
            consumers = purchase.consumers.map { ContributionRequest(it.user.id, it.price) }
        )
        val result = handleException({
            api.createPurchase(
                token,
                obj
            )
        }) {
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "createPurchase",
                type = LogType.Error,
                message = "Doesn't Handled create purchase error code: ${it}",
            )
            null
        }
        if (result.status == ApiStatus.SUCCESS)
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "createPurchase",
                type = LogType.Info,
                message = "create purchase $purchase success",
            )
        return result
    }

    suspend fun leaveGroup(token: String, groupID: Int): ApiResult<Any> {
        return handleException({
            api.leaveGroup(token, groupID)
        }) {
            if (it == 401)
                "تسویه حساب نشده است"
            else
                null
        }
    }

    suspend fun getGroupToAmount(token: String, groupId: Int, userID: Int): ApiResult<Double?> {
        val result = handleException({ api.getGroupMembers(token, groupId) }) {
            null
        }

       

        if (result.data != null) {
            result.data.forEach {
                if (it.user.id == userID) {
                    MyLog.log(
                        structureLayer = StructureLayer.Repository,
                        className = "MainRepository",
                        methodName = "getGroupToAmount",
                        type = LogType.Info,
                        message = "group with id: $groupId has amount: ${it.cost}",
                    )
                    return ApiResult.Success(it.cost)
                }
            }
            
        }
        MyLog.log(
            structureLayer = StructureLayer.Repository,
            className = "MainRepository",
            methodName = "getGroupToAmount",
            type = LogType.Error,
            message = "group with id $groupId failed to calculate amount",
        )
        return ApiResult.Error("")

    }

    suspend fun createGroup(token: String, addGroupRequest: AddGroupRequest): ApiResult<Unit> {
        val result = handleException({
            api.createGroup(token, addGroupRequest)
        }) {
            if (it == 404) {
                MyLog.log(
                    structureLayer = StructureLayer.Repository,
                    className = "MainRepository",
                    methodName = "createGroup",
                    type = LogType.Error,
                    message = "create group ${addGroupRequest.name} Failed because of invalid email",
                )
                "ایمیل اعضا معتبر نمی باشد."
            }
            else {
                MyLog.log(
                    structureLayer = StructureLayer.Repository,
                    className = "MainRepository",
                    methodName = "createGroup",
                    type = LogType.Error,
                    message = "Not handled create group error code: ${it}",
                )
                null
            }
        }

        if (result.status == ApiStatus.SUCCESS)
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "createGroup",
                type = LogType.Info,
                message = "create group ${addGroupRequest.name} success",
            )
        
        
        return result

    }

    suspend fun addUserToGroup(token: String, email: String, groupId: Int): ApiResult<Unit> {

        val result =  handleException({
            api.addUserToGroup(
                token,
                AddUserToGroupRequest(groupId, listOf(email))
            )
        }) {
            if (it == 404) {
                MyLog.log(
                    structureLayer = StructureLayer.Repository,
                    className = "MainRepository",
                    methodName = "createGroup",
                    type = LogType.Error,
                    message = "add $email to group Failed because email not found",
                )
                "ایمیل اعضا معتبر نمی باشد."
            }else {
                MyLog.log(
                    structureLayer = StructureLayer.Repository,
                    className = "MainRepository",
                    methodName = "createGroup",
                    type = LogType.Error,
                    message = "Not handled add user to group error code: ${it}",
                )
                null
            }
        }
        
        if (result.status == ApiStatus.SUCCESS)
            MyLog.log(
                structureLayer = StructureLayer.Repository,
                className = "MainRepository",
                methodName = "createGroup",
                type = LogType.Info,
                message = "add $email to group success",
            )

        return result
    }


}


data class CreatePurchaseRequest(
    val groupID: Int,
    val cost: Double,
    val description: String?,
    val date: String,
    val picture_id: Int,
    val added_by: Int,
    val buyers: List<ContributionRequest>,
    val consumers: List<ContributionRequest>
)

data class ContributionRequest(
    val userID: Int,
    val percent: Double
)
