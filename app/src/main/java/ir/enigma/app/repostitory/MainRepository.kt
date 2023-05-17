package ir.enigma.app.repostitory

import android.util.Log
import com.google.gson.Gson

import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Group
import ir.enigma.app.model.Purchase
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.network.AddUserToGroupRequest
import ir.enigma.app.network.Api
import ir.enigma.app.ui.group.*
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
        Log.d("MainRepository", "getGroupWithMembers: " + result.data?.members)
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
                ApiResult.Success(flow {
                    emit(result.data!!.groups)
                })
            }
            else -> {
                if (result.message == NO_GROUP)
                    ApiResult.Success(_data = flowOf(listOf()))
                else
                    ApiResult.Error(result.message ?: "خطا در دریافت گروه ها")
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
            null
        }

        return if (result != null) {
            ApiResult.Success(flow {
                emit(result)
            })
        } else {
            ApiResult.Error("خطا در دریافت گروه ها")
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
        Log.d("ExceptionHandler", "createPurchase: " + Gson().toJson(obj))
        return handleException({
            api.createPurchase(
                token,
                obj
            )
        }) {

            null
        }
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

    suspend fun getGroupToAmount(token: String, groupId: Int, userID: Int): ApiResult<Double> {
        return handleException({ api.getGroupDebtAndCredit(token, groupId, userID) }) {
            null
        }

    }

    suspend fun createGroup(token: String, addGroupRequest: AddGroupRequest): ApiResult<Unit> {
        return handleException({
            api.createGroup(token, addGroupRequest)
        }) {
            if (it == 404)
                "ایمیل اعضا معتبر نمی باشد."
            else
                null
        }


    }

    suspend fun addUserToGroup(token: String, email: String, groupId: Int): ApiResult<Unit> {
        // log all fields
        Log.d("ExceptionHandler", "addUserToGroup: $email $groupId")
        return handleException({
            api.addUserToGroup(
                token,
                AddUserToGroupRequest(groupId, listOf(email))
            )
        }) {
            null
        }
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
