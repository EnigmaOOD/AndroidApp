package ir.enigma.app.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class AddGroupViewModelTest : BaseViewModelTest() {

    private lateinit var authViewModel: AuthViewModel

    private lateinit var mainRepository: MainRepository
    private lateinit var addGroupViewModel: AddGroupViewModel


    @Before
    fun setup() {                                                       // Arrange for All tests
        super.setUp()
        mainRepository = mockk()
        authViewModel = AuthViewModel(userRepository)
        addGroupViewModel = AddGroupViewModel(mainRepository)
    }


    @Test
    fun `createGroup should set state to success when api result success`() {
        everyLoginSuccess()                                                         // Arrange
        everyGetUserInfoSuccess()
        coEvery { mainRepository.createGroup(any() , any()) } returns ApiResult.Success(Unit)

        authViewModel.login(context, "test", "test")                    // Act
        addGroupViewModel.createGroup(
            AddGroupRequest(
                "test",
                picture_id = 2,
                "currency",
                emails = listOf("test@gmail.com")
            )
        )

        assert(addGroupViewModel.state.value.status == ApiStatus.SUCCESS)              // Asserts
    }

    @Test
    fun `createGroup should set state to error when api result error`() {
        everyLoginSuccess()                                                         // Arrange
        everyGetUserInfoSuccess()
        coEvery { mainRepository.createGroup(any() , any()) } returns ApiResult.Error("user email not found")

        authViewModel.login(context, "test", "test")                    // Act
        addGroupViewModel.createGroup(
            AddGroupRequest(
                "test",
                picture_id = 2,
                "currency",
                emails = listOf("test")))

        assert(addGroupViewModel.state.value.status == ApiStatus.ERROR)              // Asserts
        }
}