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
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
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
        viewModelScope.launch {
            startLoading()
            when (val result = mainRepository.getGroups(token = token)) {
                is ApiResult.Success -> {
                    state.value = ApiResult.Success(Unit)
                    result.data!!.distinctUntilChanged().collect { groups ->
                        _groupList.value = groups

                        fetchGroupToAmountData(groups)
                    }

                    MyLog.log(
                        StructureLayer.ViewModel,
                        "MainViewModel",
                        "fetchGroups",
                        LogType.Info,
                        "Groups Fetched: ${result.data}"
                    )
                }
                else -> {
                    MyLog.log(
                        StructureLayer.ViewModel,
                        "MainViewModel",
                        "fetchGroups",
                        LogType.Error,
                        "Error in fetching groups: ${result.message}"
                    )
                    state.value = ApiResult.Error(result.message ?: "خطا در دریافت گروه ها")
                }
            }

        }
    }

    fun fetchGroupToAmountData(groupList: List<Group>) {
        groupList.forEach {
            groupToAmount[it.id] = mutableStateOf(null)
        }
        viewModelScope.launch {
            groupList.forEach {
                val result = mainRepository.getGroupToAmount(
                    token = token,
                    groupId = it.id,
                    userID = me.id
                )

                if (result.data != null)
                    groupToAmount[it.id]?.value = result.data
            }

            MyLog.log(
                StructureLayer.ViewModel,
                "MainViewModel",
                "fetchGroupToAmountData",
                LogType.Info,
                "fetchGroupToAmountData: $groupToAmount"
            )
        }
    }

}