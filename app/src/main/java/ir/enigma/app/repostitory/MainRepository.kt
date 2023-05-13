package ir.enigma.app.repostitory

import android.util.Log
import com.google.gson.Gson
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Group
import ir.enigma.app.model.Purchase
import ir.enigma.app.network.Api
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: Api) {


    suspend fun getGroupWithMembers(token: String, groupId: Int): ApiResult<Group> {
        val result = handleException({ api.getAGroup(token, groupId) }) {
            null
        }
        result.data?.members = handleException({ api.getGroupMembers(token, groupId) }) {
            null
        }.data
        return result
    }

    suspend fun getGroups(token: String): ApiResult<Flow<List<Group>>> {
        val result = handleException({ api.getGroups(token) }) {
            null
        }
        return when (result) {
            is ApiResult.Success -> {
                ApiResult.Success(flow {
                    emit(result.data!!.groups)
                })
            }
            else -> {
                ApiResult.Error(result.message ?: "خطا در دریافت گروه ها")
            }
        }
    }

    suspend fun getGroupPurchases(token: String, groupId: Int): ApiResult<Flow<List<Purchase>>> {
        val result = handleException({ api.getGroupPurchases(token, groupId) }) {
            null
        }
        return when (result) {
            is ApiResult.Success -> {
                ApiResult.Success(flow {
                    emit(result.data!!)
                })
            }
            else -> {
                ApiResult.Error(result.message ?: "خطا در دریافت گروه ها")
            }
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

    suspend fun leaveGroup() {
        //todo complete this
    }

    suspend fun getGroupToAmount(token: String, groupId: Int, userID: Int): ApiResult<Double> {
        return handleException({ api.getGroupDebtAndCredit(token, groupId, userID) }) {
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
