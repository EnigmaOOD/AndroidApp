package ir.enigma.app.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Group
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.auth.AuthViewModel.Companion.token
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ApiViewModel<Unit>() {

    private val _groupList = MutableStateFlow<List<Group>>(emptyList())
    val groupList = _groupList.asStateFlow()

    val groupToAmount = hashMapOf<Int, MutableState<Double?>>()

    fun fetchGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            startLading()
            when (val result = mainRepository.getGroups(token = token)) {
                is ApiResult.Success -> {
                    state.value = ApiResult.Success(Unit)
                    result.data!!.distinctUntilChanged().collect { groups ->
                        _groupList.value = groups
                        fetchGroupToAmountData(groups)
                    }

                }
                else -> {
                    fetchGroups()
                }
            }

        }
    }

    fun fetchGroupToAmountData(groupList: List<Group>) {

        viewModelScope.launch {
            groupList.forEach {
                groupToAmount[it.id] = mutableStateOf(null)
                val result = mainRepository.getGroupToAmount(
                    token = token,
                    groupId = it.id,
                    userID = me.id
                )
                if (result.data != null)
                    groupToAmount[it.id]?.value = result.data
            }
        }
    }

}