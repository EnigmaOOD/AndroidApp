package ir.enigma.app.unit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.model.UserInfo
import ir.enigma.app.repostitory.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelTest() {

    lateinit var userRepository: UserRepository
    lateinit var context: Context

    companion object {

        val mockUser1 = User(1, "test", "test", 2)
        val mockUser2 = User(2, "test2", "test2", 5)
    }


    private val dispatcher = UnconfinedTestDispatcher()

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        dispatcher.cancel()
    }

    open fun setUp() {
        Dispatchers.setMain(dispatcher)
        context = ApplicationProvider.getApplicationContext()
        this.userRepository = mockk()
    }

    // utils
    fun everyLoginSuccess() {
        coEvery {
            userRepository.login(
                any(),
                any()
            )
        } returns ApiResult.Success(Token("token"))
    }

    fun everyLoginError() {
        coEvery {
            userRepository.login(
                any(),
                any()
            )
        } returns ApiResult.Error("wrong email or password")
    }

    fun everyRegisterSuccess() {
        coEvery { userRepository.register(any()) } returns ApiResult.Success(mockUser1)
    }

    fun everyRegisterError() {
        coEvery { userRepository.register(any()) } returns ApiResult.Error("email exist")
    }

    fun everyGetUserInfoSuccess() {
        coEvery { userRepository.getUserInfo(any()) } returns ApiResult.Success(UserInfo(mockUser1))
    }

    fun everyGetUserInfoError() {
        coEvery { userRepository.getUserInfo(any()) } returns ApiResult.Error("token expired")
    }
}