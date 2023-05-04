package ir.enigma.app.ui.group


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Group
import ir.enigma.app.model.Purchase
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.ui.auth.AuthViewModel.Companion.token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ApiViewModel<Group>() {

    val purchaseState = mutableStateOf<ApiResult<Unit>>(ApiResult.Loading());
    private val _purchaseList = MutableStateFlow<List<Purchase>>(emptyList())
    val purchaseList = _purchaseList.asStateFlow()

    fun fetchGroupData(groupId: Int) {
        viewModelScope.launch {
            state.value = mainRepository.getGroupWithMembers(token = token, groupId = groupId)
            if (state.value.status == ApiStatus.SUCCESS) {
                fetchPurchases(groupId)
            }
        }

    }

    fun fetchPurchases(groupId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseState.value = ApiResult.Loading()
            val group = state.value.data
            if (group == null) {
                fetchGroupData(groupId)
                return@launch
            }
            when (val result =
                mainRepository.getGroupPurchases(token = token, groupId = group.id)) {
                is ApiResult.Success -> {
                    purchaseState.value = ApiResult.Success(Unit)
                    result.data!!.distinctUntilChanged().collect { purchases ->
                        _purchaseList.value = purchases
                    }
                }
                else -> {
                    state.value = result as ApiResult<Group>
                }
            }

        }
    }

}