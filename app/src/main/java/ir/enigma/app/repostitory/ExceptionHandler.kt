package ir.enigma.app.repostitory

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
        val data = call()
        if (data.errorBody() != null) {
            try {
                val errorString = data.errorBody()!!.string()
            } catch (e: JSONException) {
            } catch (e: IOException) {
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