package ir.enigma.app
import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


class TestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        appName: String,
        context: Context) : Application {
        return super.newApplication(
            cl, App::class.java.name, context)
    }
}