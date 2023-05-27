package ir.enigma.app.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Group
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.main.MainViewModel
import ir.enigma.app.unit.GroupViewModelTest.Companion.mockGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseViewModelTest() {

    private lateinit var mainRepository: MainRepository
    private lateinit var mainViewModel: MainViewModel
    lateinit var authViewModel: AuthViewModel

    val mockGroups = listOf(mockGroup)


    @Before
    override fun setUp() {
        super.setUp()
        authViewModel = AuthViewModel(userRepository)
        mainRepository = mockk()
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun `fetchGroups should update state with success Act repository returns success`() {
        // Arrange
        login()
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Success(flowOf(mockGroups))
        coEvery { mainRepository.getGroupToAmount(any() , any() , any()) } returns ApiResult.Success(2.0)

        // Act
        mainViewModel.fetchGroups()

        //Assert
        assertEquals(mainViewModel.state.value.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `fetchGroups should retry fetching groups Act repository returns error`() {
        // Arrange
        login()
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Error("group is empty")

        // Act
        mainViewModel.fetchGroups()

        //Assert
        assertEquals(mainViewModel.state.value.status, ApiStatus.ERROR)
        assertEquals(mainViewModel.state.value.message, "group is empty")

    }

    @Test
    fun `fetchGroupToAmountData should update groupToAmount with data from repository`() {
        // Arrange
        login()
        val group1 = mockGroup

        val groupList = listOf(group1)
        val amount1 = 50.0

        coEvery {
            mainRepository.getGroupToAmount(any(), group1.id, any())
        } returns ApiResult.Success(amount1)


        // Act
        mainViewModel.fetchGroupToAmountData(groupList)

        // Assert
        assertEquals(amount1, mainViewModel.groupToAmount[group1.id]?.value)

    }


    private fun login() {
        everyLoginSuccess()
        everyGetUserInfoSuccess()
        authViewModel.login(sharedPrefManager, "test", "test")
    }
}