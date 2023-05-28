package ir.enigma.app.unit

import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.model.UserInfo
import ir.enigma.app.network.Api
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.repostitory.UserRepository.Companion.EMAIL_EXIST
import ir.enigma.app.repostitory.UserRepository.Companion.UN_AUTHORIZE_ERROR
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockLog
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockUser1
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryTest {

    private lateinit var api: Api
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        mockLog()
        api = mockk()
        userRepository = UserRepository(api)
    }

    @Test
    fun `login should return token when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.login(any(), any()) } returns Response.success(Token("tokenTest"))

        //Act: call repository function
        val response = userRepository.login(email = "test@test.com", password = "123")

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data, Token("tokenTest"))

    }

    @Test
    fun `login should return suitable error when api result is 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.login(any(), any()) } returns Response.error(
            400,
            "ایمیل یا رمز عبور اشتباه است".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = userRepository.login(email = "test@test.com", password = "123")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "ایمیل یا رمز عبور اشتباه است")

    }

    @Test
    fun `login should return suitable error when api error when api result is 500 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.login(any(), any()) } returns Response.error(
            500,
            "".toResponseBody()
        )

        //Act: call repository function
        val response = userRepository.login(email = "test", password = "123")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "با عرض پوزش خطایی در سرور رخ داده است. لطفا بعدا تلاش کنید")

    }


    @Test
    fun `register should return user when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.register(any()) } returns Response.success(mockUser1)

        //Act: call repository function
        val response = userRepository.register(mockUser1)

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data?.email, mockUser1.email)

    }

    @Test
    fun `register should return error when api result is 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.register(any()) } returns Response.error(
            400,
            EMAIL_EXIST.toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = userRepository.register(mockUser1)

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, EMAIL_EXIST)

    }

    @Test
    fun `register should return error when api result is 404 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.register(any()) } returns Response.error(
            404,
            "".toResponseBody()
        )

        //Act: call repository function
        val response = userRepository.register(mockUser1)

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "با عرض پوزش خطایی غیر منتظره رخ داده است.")

    }

    @Test
    fun `getUserInfo should return user information when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.userInfo(any()) } returns Response.success(UserInfo(mockUser1))

        //Act: call repository function
        val response = userRepository.getUserInfo("testToken")

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data?.user?.id, mockUser1.id)

    }

    @Test
    fun `getUserInfo should return suitable error when api result is 401 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.userInfo(any()) } returns Response.error(
            401,
            UN_AUTHORIZE_ERROR.toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = userRepository.getUserInfo("testToken")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, UN_AUTHORIZE_ERROR)

    }

    @Test
    fun `getUserInfo should return error when api suitable error when api result is 404 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.userInfo(any()) } returns Response.error(
            404,
            "".toResponseBody()
        )

        //Act: call repository function
        val response = userRepository.getUserInfo("testToken")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "با عرض پوزش خطایی غیر منتظره رخ داده است.")

    }


    @Test
    fun `editProfile should be success when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.editProfile(any(), any(), any()) } returns Response.success(null)

        //Act: call repository function
        val response = userRepository.editProfile("testToken", "test", 0)

        //Assert: response should be success
        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `editProfile should return suitable error when api result is 401 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.editProfile(any(), any(), any()) } returns Response.error(
            401,
            "".toResponseBody()
        )

        //Act: call repository function
        val response = userRepository.editProfile("testToken", "test", 0)

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "با عرض پوزش خطایی غیر منتظره رخ داده است.")

    }

    @Test
    fun `editProfile should return suitable error when api result is 404 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.editProfile(any(), any(), any()) } returns Response.error(
            404,
            "".toResponseBody()
        )

        //Act: call repository function
        val response = userRepository.editProfile("testToken", "test", 0)

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "با عرض پوزش خطایی غیر منتظره رخ داده است.")

    }

}