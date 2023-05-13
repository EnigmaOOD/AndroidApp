package ir.enigma.app.repostitory

import android.util.Log
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Group
import ir.enigma.app.model.Purchase
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.network.Api
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: Api) {


    suspend fun getGroupWithMembers(token: String, groupId: Int): ApiResult<Group> {
        val result = handleException({ api.getAGroup(token, groupId) }) {
            null
        }
        handleException({ api.getGroupMembers(token, groupId) }) {
            null
        }.let {
            if (it is ApiResult.Success) {
                result.data!!.members = it.data!!
            }
        }
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

    suspend fun getGroupPurchases(
        token: String,
        groupId: Int,
    ): ApiResult<Flow<List<Purchase>>> {
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
}

