package ir.enigma.app.util

import android.content.Context
import android.os.Process
import android.util.Log

import ir.enigma.app.BuildConfig
import java.io.File

object MyLog {

    private var directory: String? = null
    private const val TAG = "MyLog"
    fun log(
        structureLayer: StructureLayer,
        className: String,
        methodName: String = "",
        type: LogType,
        message: String,
        exception: Exception? = null,
        onlyInDebugMode: Boolean = false
    ) {
        if (onlyInDebugMode && !BuildConfig.DEBUG) return
        val tag = structureLayer.name
        val logMessage = if (methodName.isEmpty()) {
            "$className: $message"
        } else {
            "$className.$methodName: $message"
        }
        when (type) {
            LogType.Info -> {
                if (exception == null) {
                    Log.i(tag, logMessage)
                } else {
                    Log.i(tag, logMessage, exception)
                }
            }
            LogType.Error -> {
                if (exception == null) {
                    Log.e(tag, logMessage)
                } else {
                    Log.e(tag, logMessage, exception)
                }
            }
            LogType.Warning -> {
                if (exception == null) {
                    Log.w(tag, logMessage)
                } else {
                    Log.w(tag, logMessage, exception)
                }
            }
            LogType.Debug -> {
                if (exception == null) {
                    Log.d(tag, logMessage)
                } else {
                    Log.d(tag, logMessage, exception)
                }
            }
        }

        saveLogToFile()

    }

    fun initLogSaving(context: Context) {
        try {

            val path = File(
                context.getExternalFilesDir("Giringi log")!!.path
            )
            if (!path.exists()) {
                path.mkdir()
            }
            directory = path.path
            Log.d(TAG, "initLogSaving: $directory")
            saveLogToFile()
        } catch (e: Exception) {
            Log.e("Exception", "initLogSaving: ", e)
        }
    }

    private fun saveLogToFile() {
        try {

            if (directory == null) {
                return
            }
            val logFile = File("$directory" + File.separator + "log.txt")
            val pid = Process.myPid()
            Runtime.getRuntime().exec("logcat -d --pid=$pid -f $logFile")

        } catch (e: Exception) {
            Log.e(TAG, "saveLogToFile: ", e)
        }
    }

}

enum class LogType {
    Info,
    Error,
    Warning,
    Debug,
}

enum class StructureLayer {
    Network,
    Repository,
    Util,
    Screen,
    ViewModel,
}