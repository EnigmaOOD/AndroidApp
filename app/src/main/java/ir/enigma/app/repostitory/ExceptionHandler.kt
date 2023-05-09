package ir.enigma.app.repostitory

import android.util.Log
import ir.enigma.app.data.ApiResult
import retrofit2.Response

fun generalErrorHandler(code: Int): String {

    return if (code / 100 == 5) {
        "با عرض پوزش خطایی در سرور رخ داده است. لطفا بعدا تلاش کنید"
    } else
        "با عرض پوزش خطایی غیر منتظره رخ داده است."
}

suspend fun <T> handleException(
    call: suspend () -> Response<T>,
    errorCodeHande: (code: Int) -> String?
): ApiResult<T> {
    val result = try {
        val data = call()
        Log.d("ExceptionHandler", "handleException: " + data)
        Log.d("ExceptionHandler", "handleExceptionErrorBody: ${data.errorBody()}")
        if (data.isSuccessful)
            ApiResult.Success(data.body())
        else {
            val message = errorCodeHande(data.code())
            if (message != null)
                ApiResult.Error(message)
            else
                ApiResult.Error(generalErrorHandler(data.code()))
        }
    } catch (e: Exception) {
        Log.e("ExceptionHandler", "handleException: ", e)
        ApiResult.Error("اتصال به اینترنت برقرار نیست")
    }
    Log.d("ExceptionHandler", "handleException: $result")
    return result
}