package ir.enigma.app.ui.group


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Group
import ir.enigma.app.model.Member
import ir.enigma.app.model.Purchase
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.auth.AuthViewModel.Companion.token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ApiViewModel<Group>() {

    val purchaseState = mutableStateOf<ApiResult<Unit>>(ApiResult.Empty());
    private val _purchaseList = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseList = _purchaseList.asStateFlow()

    val newMemberState = mutableStateOf<ApiResult<Unit>>(ApiResult.Empty())

    var meMember: Member? = null

    val newPurchaseState = mutableStateOf<ApiResult<Any>>(
        ApiResult.Empty()
    )

    val leaveGroupState = mutableStateOf<ApiResult<Any>>(ApiResult.Empty())

    fun fetchGroupData(groupId: Int, filter: Int = 0) {
        viewModelScope.launch {
            startLading()
            val result = mainRepository.getGroupWithMembers(token = token, groupId = groupId)
            if (result is ApiResult.Success && result.data?.members != null) {
                meMember = result.data.members!!.find { it.user.id == me.id }
                state.value = ApiResult.Success(result.data)
                Log.d("GroupViewModel", "fetchGroupData: ${result.data?.members}")
                fetchPurchases(groupId, filter)
            } else {
                fetchGroupData(groupId)
            }

        }
    }

    fun fetchPurchases(groupId: Int, filter: Int = 0) {
        viewModelScope.launch {
            purchaseState.value = ApiResult.Loading()
            val group = state.value.data
            if (group == null) {
                fetchGroupData(groupId)
                return@launch
            }
            when (val result =
                mainRepository.getGroupPurchases(token = token, groupId = group.id, filter)) {
                is ApiResult.Success -> {
                    purchaseState.value = ApiResult.Success(Unit)
                    result.data!!.distinctUntilChanged().collect { purchases ->
                        _purchaseList.value = purchases
                    }
                }
                else -> {
                    fetchPurchases(groupId)
                }
            }

        }
    }

    fun createPurchase(purchase: Purchase) {
        viewModelScope.launch {
            newPurchaseState.value = ApiResult.Loading()
            val group = state.value.data
            val result = mainRepository.createPurchase(
                token = token,
                groupId = group!!.id,
                purchase = purchase
            )
            if (result.status == ApiStatus.SUCCESS)
                fetchPurchases(state.value.data!!.id)
            newPurchaseState.value = result
        }
    }

    fun leaveGroup(groupID: Int) {
        viewModelScope.launch {
            leaveGroupState.value = ApiResult.Loading()
            leaveGroupState.value =
                mainRepository.leaveGroup(token = token, groupID = groupID)

        }
    }

    fun addMember(email: String) {
        viewModelScope.launch {
            newMemberState.value = ApiResult.Loading()
            val result = mainRepository.addUserToGroup(token, email, state.value.data!!.id)
            if (result.status == ApiStatus.SUCCESS)
                fetchGroupData(state.value.data!!.id)
            newMemberState.value = result
        }
    }

    fun newMemberReset(){
        newMemberState.value = ApiResult.Empty()
    }

    fun newPurchaseReset(){
        newPurchaseState.value = ApiResult.Empty()
    }

    fun leaveStateReset(){
        leaveGroupState.value = ApiResult.Empty()
    }
}