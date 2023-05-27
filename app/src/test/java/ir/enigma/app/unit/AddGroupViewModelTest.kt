package ir.enigma.app.unit

import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.auth.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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

        authViewModel.login(super.sharedPrefManager, "test", "test")                    // Act
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

        authViewModel.login(sharedPrefManager, "test", "test")                    // Act
        addGroupViewModel.createGroup(
            AddGroupRequest(
                "test",
                picture_id = 2,
                "currency",
                emails = listOf("test")))

        assert(addGroupViewModel.state.value.status == ApiStatus.ERROR)              // Asserts
        }
}