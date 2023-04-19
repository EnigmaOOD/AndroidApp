package ir.enigma.app.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.enigma.app.data.ApiResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class ApiViewModel<T> : ViewModel() {
    var state: MutableState<ApiResult<T>> = mutableStateOf(ApiResult.Empty())


    fun startLading() {
        state.value = ApiResult.Loading()
    }


    fun error(message: String) {
        state.value = ApiResult.Error(message)
    }

    fun success(data: T) {
        state.value = ApiResult.Success(data)
    }

    fun callApi(apiCall: suspend () -> ApiResult<T>) {
        viewModelScope.launch {
            startLading()
            state.value = apiCall()
        }
    }
}