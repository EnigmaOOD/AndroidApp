package ir.enigma.app.ui.main

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

    fun fetchGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            startLading()
            when (val result = mainRepository.getGroups(token = token)) {
                is ApiResult.Success -> {
                    state.value = ApiResult.Success(Unit)
                    result.data!!.distinctUntilChanged().collect { groups ->
                        _groupList.value = groups
                    }
                }
                else -> {
                    fetchGroups()
                }
            }

        }
    }

}