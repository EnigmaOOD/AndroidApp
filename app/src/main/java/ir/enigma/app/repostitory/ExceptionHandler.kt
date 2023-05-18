package ir.enigma.app.repostitory

import android.util.Log
import com.google.gson.Gson
import ir.enigma.app.data.ApiResult
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException


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
        Log.d("ExceptionHandler", "handleException: $call")
        val data = call()
        Log.d("ExceptionHandler", "handleException: $data")
        if (data.errorBody() != null) {
            try {
                val errorString = data.errorBody()!!.string()
                Log.d("ExceptionHandler", "handleException: Errod body $errorString")
            } catch (e: JSONException) {
                Log.e("ExceptionHandler", "handleException: ", e)
            } catch (e: IOException) {
                Log.e("ExceptionHandler", "handleException: ", e)
            }
        }
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

        ApiResult.Error("اتصال به اینترنت برقرار نیست")
    }

    return result

}