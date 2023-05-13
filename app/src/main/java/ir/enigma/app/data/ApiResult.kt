package ir.enigma.app.data

sealed class ApiResult<out T>(val status: ApiStatus, val data: T?, var message: String?) {

    data class Success<out R>(val _data: R?, val _message: String? = null) : ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = _message
    )

    data class Error(val _message: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = _message
    )

    class Loading() : ApiResult<Nothing>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )

    class Empty() : ApiResult<Nothing>(
        status = ApiStatus.EMPTY,
        data = null,
        message = null
    )

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message]"
            is Loading -> "Loading"
            is Empty -> "Empty"
        }
    }


}
