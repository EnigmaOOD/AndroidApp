package ir.enigma.app.unit

import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.User
import ir.enigma.app.ui.auth.AuthViewModel
import junit.framework.TestCase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test


class AuthViewModelTest : BaseViewModelTest() {

    private lateinit var authViewModel: AuthViewModel


    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()

        authViewModel = AuthViewModel(userRepository)
    }


    // test for register, setMe and saveToken in SharedPreference
    @Test
    fun `register should set state to success and set message to verify email when api result is success`() {
        everyRegisterSuccess()                                                                       //Arrange
        everyLoginSuccess()
        val user = User(0, "test", name = "test", 12, "test")

        authViewModel.register(
            user.name,
            user.email,
            user.iconId,
            user.password!!
        )                                                                                             //Act


        assert(authViewModel.state.value is ApiResult.Success)                                        //Assert
        assertEquals(authViewModel.state.value.message, AuthViewModel.EMAIL_VERIFICATION)
    }

    @Test
    fun `register should error when api result is error`() {
        coEvery { userRepository.register(any()) } returns ApiResult.Error("email exist")       //Arrange

        val user = User(0, "test", name = "test", 12, "test")
        authViewModel.register(
            user.name,
            user.email,
            user.iconId,
            user.password!!
        )                                                                                            //Act

        assert(authViewModel.state.value is ApiResult.Error)                                        //Assert
        assert(authViewModel.state.value.message == "email exist")
    }


    @Test
    fun `login should success and set token and user info when api result is success`() {
        // Arrange
        everyLoginSuccess()
        everyGetUserInfoSuccess()

        val user =
            User(0, "test", name = "test", 12, "test")

        authViewModel.login(
            sharedPrefManager,
            user.email,
            user.password!!
        )                                                                                            // Act


        val token = authViewModel.state.value.data?.token
        assert(authViewModel.state.value is ApiResult.Success)                                       // Asserts
        assert(token == AuthViewModel.token)
        assert(AuthViewModel.me.email == user.email)
    }

    //login when wrong inputs
    @Test
    fun `login should error when api result is error`() {
        everyLoginError()                                                                 //Arrange

        val user = User(0, "test", name = "test", 12, "test")
        authViewModel.login(
            sharedPrefManager,
            user.email,
            user.password!!
        )                                                                                  //Act

        assert(authViewModel.state.value is ApiResult.Error)                                //Assert
        assert(authViewModel.state.value.message == "wrong email or password")
    }

    // checkfortoken when token exist in shared preference
    @Test
    fun `checkForToken should load token and set user info when token exist in shared preference`() {
        //Arrange
        everyGetUserInfoSuccess()

        authViewModel.checkForToken(sharedPrefManager)                                   //Act

        assert(authViewModel.state.value is ApiResult.Success)                           //Assert
        assert(authViewModel.state.value.data?.token == "token")
        assert(AuthViewModel.me.email == mockUser1.email)
    }


    @Test
    fun `checkForToken should set state to empty when token not exist in shared preference`() {
        coEvery {
            sharedPrefManager.getString(
                any(),
                any()
            )
        } returns null                     //Arrange
        coEvery { sharedPrefManager.getString(any()) } returns null                     //Arrange

        authViewModel.checkForToken(sharedPrefManager)                                              //Act

        assert(authViewModel.state.value is ApiResult.Empty)                              //Assert
    }

    @Test
    fun `checkForToken should set state to error when token is expired`() {
        //Arrange
        everyGetUserInfoError()

        authViewModel.checkForToken(sharedPrefManager)                                                //Act

        assert(authViewModel.state.value is ApiResult.Error)                                  //Assert
        assert(authViewModel.state.value.message == "token expired")
    }


    @Test
    fun `editProfile should change user name and icon id when api result is success`() {
        everyLoginSuccess()
        everyGetUserInfoSuccess()
        coEvery {
            userRepository.editProfile(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(Unit)                                                   //Arrange

        authViewModel.login(
            sharedPrefManager,
            mockUser1.email,
            mockUser1.email
        )                                                                                  //Act: login with user1
        authViewModel.editProfile(
            mockUser2.name,
            mockUser2.iconId
        )                                                                                   //Act: edit profile to user2

        assert(authViewModel.editUserState.value is ApiResult.Success)                                                          //Assert
        assert(AuthViewModel.me.name == mockUser2.name)
        assert(AuthViewModel.me.iconId == mockUser2.iconId)
    }


    @Test
    fun `editProfile should not change user name and icon id when api result is error`() {
        everyLoginSuccess()
        everyGetUserInfoSuccess()
        coEvery {
            userRepository.editProfile(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Error("error")                                       //Arrange

        authViewModel.login(sharedPrefManager, mockUser1.email, mockUser1.email)
        authViewModel.editProfile(
            mockUser2.name,
            mockUser2.iconId
        )                                                                                  //Act

        assert(authViewModel.editUserState.value is ApiResult.Error)                        //Assert
        assert(AuthViewModel.me.name == mockUser1.name)
        assert(AuthViewModel.me.iconId == mockUser1.iconId)
    }


    @Test
    fun `logout should set state to empty`() {
        everyLoginSuccess()
        everyGetUserInfoSuccess()

        authViewModel.login(sharedPrefManager, mockUser1.email, mockUser1.email)
        authViewModel.logout(sharedPrefManager)                                              //Act


        assertEquals(authViewModel.state.value.status, ApiStatus.EMPTY)            //Assert
    }

    @Test
    fun `editFinish should set edit user state to empty`(){
        authViewModel.editFinish()                                                          //Act

        assertEquals(authViewModel.editUserState.value.status, ApiStatus.EMPTY)            //Assert
    }
}
