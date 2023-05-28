package ir.enigma.app.repostitory

import android.util.Log
import com.google.gson.Gson
import ir.enigma.app.data.ApiResult
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
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
                MyLog.log(
                    StructureLayer.Network,
                    "ExceptionHandler",
                    "handleException",
                    LogType.Error,
                    "errorString: $errorString in call: $call"
                )
            } catch (e: Exception) {
                MyLog.log(
                    StructureLayer.Network,
                    "ExceptionHandler",
                    "handleException",
                    LogType.Error,
                    "api call errorString: ${e.message} in call: $call"
                )
            }
        }
        if (data.isSuccessful) {
            MyLog.log(
                StructureLayer.Network,
                "ExceptionHandler",
                "handleException",
                LogType.Info,
                "api call success: ${Gson().toJson(data.body())} in call: $call"
            )
            ApiResult.Success(data.body())
        }
        else {
            val message = errorCodeHande(data.code())
            MyLog.log(
                StructureLayer.Network,
                "ExceptionHandler",
                "handleException",
                LogType.Error,
                "api call errorCode: ${data.code()} message: $message in call: $call"
            )

            if (message != null)
                ApiResult.Error(message)
            else
                ApiResult.Error(generalErrorHandler(data.code()))

        }
    } catch (e: Exception) {
        MyLog.log(
            StructureLayer.Network,
            "ExceptionHandler",
            "handleException",
            LogType.Error,
            "errorString: ${e.message} in call: $call"
        )

        ApiResult.Error("اتصال به اینترنت برقرار نیست")
    }

    return result

}