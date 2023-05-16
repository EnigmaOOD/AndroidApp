package ir.enigma.app.unit.utils
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.enigma.app.util.SharedPrefManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPrefManagerTest {

    private lateinit var sharedPrefManager: SharedPrefManager

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPrefManager = SharedPrefManager(context)
        sharedPrefManager.putString("name", "John")
        sharedPrefManager.putBoolean("is_logged_in", true)
        sharedPrefManager.putInt("age", 30)
        sharedPrefManager.putLong("timestamp", System.currentTimeMillis())
        sharedPrefManager.putFloat("score", 4.5F)
    }

    @Test
    fun testGetString() {
        val name = sharedPrefManager.getString("name")
        assertEquals("John", name)
    }

    @Test
    fun testGetStringWithDefaultValue() {
        val nickname = sharedPrefManager.getString("nickname", "Johnny")
        assertEquals("Johnny", nickname)
    }

    @Test
    fun testGetBoolean() {
        val isLoggedIn = sharedPrefManager.getBoolean("is_logged_in")
        assertEquals(true, isLoggedIn)
    }

    @Test
    fun testGetBooleanWithDefaultValue() {
        val isPremiumUser = sharedPrefManager.getBoolean("is_premium_user", false)
        assertEquals(false, isPremiumUser)
    }

    @Test
    fun testGetInt() {
        val age = sharedPrefManager.getInt("age")
        assertEquals(30, age)
    }

    @Test
    fun testGetIntWithDefaultValue() {
        val weight = sharedPrefManager.getInt("weight", 70)
        assertEquals(70, weight)
    }

    @Test
    fun testGetLong() {
        val timestamp = sharedPrefManager.getLong("timestamp")
        assertEquals(true, timestamp > 0L)
    }

    @Test
    fun testGetLongWithDefaultValue() {
        val lastLogin = sharedPrefManager.getLong("last_login", System.currentTimeMillis())
        assertEquals(true, lastLogin > 0L)
    }

    @Test
    fun testGetFloat() {
        val score = sharedPrefManager.getFloat("score")
        assertEquals(4.5F, score, 0.0F)
    }

}