package ir.enigma.app.unit

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.model.UserInfo
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelTest() {

    lateinit var userRepository: UserRepository

    lateinit var sharedPrefManager: SharedPrefManager

    companion object {

        val mockUser1 = User(1, "test", "test", 2)
        val mockUser2 = User(2, "test2", "test2", 5)

        fun mockLog(){
            mockkObject(MyLog)
            coEvery { MyLog.log(any(), any(), any(), any(), any()) } returns Unit
        }
    }


    private val dispatcher = UnconfinedTestDispatcher()

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        dispatcher.cancel()
    }

    open fun setUp() {
        mockLog()
        Dispatchers.setMain(dispatcher)
        sharedPrefManager = mockk()
        this.userRepository = mockk()
    }

    // utils
    fun everyLoginSuccess() {
        coEvery { sharedPrefManager.getString(any()) } returns "token"
        coEvery { sharedPrefManager.getString(any() , any()) } returns "token"
        coEvery { sharedPrefManager.putString(any() , any()) } returns Unit
        coEvery {
            userRepository.login(
                any(),
                any()
            )
        } returns ApiResult.Success(Token("token"))
    }

    fun everyLoginError() {
        coEvery { sharedPrefManager.getString(any()) } returns "token"
        coEvery { sharedPrefManager.getString(any() , any()) } returns "token"
        coEvery { sharedPrefManager.putString(any() , any()) } returns Unit
        coEvery {
            userRepository.login(
                any(),
                any()
            )
        } returns ApiResult.Error("wrong email or password")
    }

    fun everyRegisterSuccess() {
        coEvery { userRepository.register(any()) } returns ApiResult.Success(
            _data = null,
            _message = AuthViewModel.EMAIL_VERIFICATION
        )
    }

    fun everyRegisterError() {

        coEvery { userRepository.register(any()) } returns ApiResult.Error("email exist")
    }

    fun everyGetUserInfoSuccess() {
        coEvery { sharedPrefManager.getString(any()) } returns "token"
        coEvery { sharedPrefManager.getString(any() , any()) } returns "token"
        coEvery { sharedPrefManager.putString(any() , any()) } returns Unit
        coEvery { userRepository.getUserInfo(any()) } returns ApiResult.Success(UserInfo(mockUser1))
    }

    fun everyGetUserInfoError() {
        coEvery { sharedPrefManager.getString(any()) } returns "token"
        coEvery { sharedPrefManager.getString(any() , any()) } returns "token"
        coEvery { userRepository.getUserInfo(any()) } returns ApiResult.Error("token expired")
    }
}