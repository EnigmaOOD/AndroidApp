package ir.enigma.app.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ir.enigma.app.data.ApiResult
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer

open class ApiViewModel<T> : ViewModel() {
    var state: MutableState<ApiResult<T>> = mutableStateOf(ApiResult.Empty())


    fun startLoading() {
        MyLog.log(
            StructureLayer.ViewModel,
            "ApiViewModel",
            "startLading",
            LogType.Info,
            "startLoading from $this"
        )
        state.value = ApiResult.Loading()
    }

    fun empty() {
        MyLog.log(
            StructureLayer.ViewModel,
            "ApiViewModel",
            "empty",
            LogType.Info,
            "empty from $this"
        )
        state.value = ApiResult.Empty()
    }

    fun error(message: String) {
        MyLog.log(
            StructureLayer.ViewModel,
            "ApiViewModel",
            "error",
            LogType.Error,
            "error from $this"
        )
        state.value = ApiResult.Error(message)
    }

    fun success(data: T) {
        MyLog.log(
            StructureLayer.ViewModel,
            "ApiViewModel",
            "success",
            LogType.Info,
            "success from $this  data: $data"
        )
        state.value = ApiResult.Success(data)
    }


}