package ir.enigma.app.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.*
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.group.GroupViewModel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
class GroupViewModelTest : BaseViewModelTest() {

    private lateinit var groupViewModel: GroupViewModel
    private lateinit var authViewModel: AuthViewModel
    private val mainRepository = mockk<MainRepository>()

    companion object {

        val mockGroup =
            Group.Builder()
                .id(1)
                .name("test")
                .categoryId(2)
                .currency("test")
                .members(listOf(Member(mockUser1, 2000.0), Member(mockUser2, 1000.0)))
                .build()
    }


    val mockPurchase1 = Purchase(
        title = "test",
        "2022-02-02",
        totalPrice = 2000.0,
        sender = User(1, "test", "test", 2, "test"),
        purchaseCategoryIndex = 2,
        buyers = listOf(
            Contribution(
                User(1, "test", "test", 2, "test"), 2000.0
            ),
        ),
        consumers = listOf(
            Contribution(
                User(2, "test2", "test2", 5, "test2"), 2000.0
            ),
        )
    )


    val mockPurchase2 = Purchase(
        title = "test2",
        "2022-02-02",
        totalPrice = 2000.0,
        sender = User(1, "test", "test", 2, "test"),
        purchaseCategoryIndex = 2,
        buyers = listOf(
            Contribution(
                User(2, "test2", "test2", 5, "test2"), 2000.0
            ),
        ),
        consumers = listOf(
            Contribution(
                User(1, "test", "test", 2, "test"), 2000.0
            ),
        )
    )


    @Before
    override fun setUp() {
        super.setUp()
        groupViewModel = GroupViewModel(mainRepository)
        authViewModel = AuthViewModel(userRepository)
    }


    private fun login() {
        everyLoginSuccess()
        everyGetUserInfoSuccess()
        authViewModel.login(sharedPrefManager, "test", "test")
    }

    @Test
    fun `fetchGroupData should update state and set group members when api call is successful`() {
        // Arrange
        login()
        val groupId = mockGroup.id

        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        coEvery { mainRepository.getGroupPurchases(any(), any(), any()) } returns ApiResult.Success(
            flowOf(listOf(mockPurchase1, mockPurchase2))
        )

        // Act
        groupViewModel.fetchGroupData(groupId)

        // Assert
        assertEquals(groupViewModel.state.value.status, ApiStatus.SUCCESS)
        assert(groupViewModel.state.value.data?.members?.size == 2)
        assert(groupViewModel.meMember != null)
    }


    @Test
    fun `fetchPurchases should update purchase list when api call is successful`() {
        //Arrange
        login()
        val mockPurchases = listOf(mockPurchase1, mockPurchase2)
        coEvery {
            mainRepository.getGroupWithMembers(any(), any())
        } returns ApiResult.Success(mockGroup)
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(mockPurchases))
        groupViewModel.state.value = ApiResult.Success(mockGroup)

        // Act
        groupViewModel.fetchPurchases(mockGroup.id)

        // Assert
        assertEquals(groupViewModel.purchaseState.value.status, ApiStatus.SUCCESS)
        assert(groupViewModel.purchaseList.replayCache.first() == mockPurchases)
        assertEquals(groupViewModel.state.value.status, ApiStatus.SUCCESS)
    }

    @Test
    fun `fetchPurchases should call fetchGroupData when group is null`() {
        //Arrange
        login()
        val mockPurchases = listOf(mockPurchase1, mockPurchase2)
        coEvery {
            mainRepository.getGroupWithMembers(any(), any())
        } returns ApiResult.Success(mockGroup)
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(mockPurchases))

        // Act
        groupViewModel.fetchPurchases(mockGroup.id)

        // Assert
        coVerify { mainRepository.getGroupPurchases(any(), any(), any()) }
        assertEquals(groupViewModel.purchaseState.value.status, ApiStatus.SUCCESS)
        assert(groupViewModel.purchaseList.replayCache.first() == mockPurchases)
        assertNotNull(groupViewModel.meMember)
        assertEquals(groupViewModel.state.value.status, ApiStatus.SUCCESS)
    }

    @Test
    fun `createPurchase should update newPurchaseState when api call is successful`() {
        //Arrange
        login()
        val mockPurchase = mockk<Purchase>()
        coEvery { mainRepository.getGroupPurchases(any(), any(), any()) } returns ApiResult.Success(
            flowOf(listOf(mockPurchase1, mockPurchase2))
        )
        coEvery {
            mainRepository.createPurchase(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(Unit)

        // Act
        groupViewModel.state.value = ApiResult.Success(mockGroup)
        groupViewModel.createPurchase(mockPurchase)

        // Assert
        assert(groupViewModel.newPurchaseState.value == ApiResult.Success(Unit))
    }

    @Test
    fun `leaveGroup should call mainRepository's leaveGroup and set leaveGroupState when api call is successful`() {
        //Arrange
        login()
        coEvery { mainRepository.leaveGroup(any(), any()) } returns ApiResult.Success(Unit)

        // Act
        groupViewModel.leaveGroup(mockGroup.id)

        // Assert
        coVerify { mainRepository.leaveGroup(any(), mockGroup.id) }
        assertEquals(ApiStatus.SUCCESS, groupViewModel.leaveGroupState.value.status)
    }

    @Test
    fun `leaveGroup should call mainRepository's leaveGroup and set leaveGroupState to error when api result is error`() {
        //Arrange
        login()
        coEvery {
            mainRepository.leaveGroup(
                any(),
                any()
            )
        } returns ApiResult.Error("no saddle up")

        // Act
        groupViewModel.leaveGroup(mockGroup.id)

        // Assert
        coVerify { mainRepository.leaveGroup(any(), mockGroup.id) }
        assertEquals(ApiStatus.ERROR, groupViewModel.leaveGroupState.value.status)
        assertEquals("no saddle up", groupViewModel.leaveGroupState.value.message)
    }


    @Test
    fun `fetchPurchases should call fetchGroupData when state value has null data`() {
        //Arrange
        login()

        val groupId = mockGroup.id
        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(listOf(mockk())))

        // Act
        groupViewModel.state.value = ApiResult.Success(null)
        groupViewModel.fetchPurchases(groupId)

        // Assert
        coVerify { mainRepository.getGroupWithMembers(any(), groupId) }

    }


    @Test
    fun `createPurchase should call fetchPurchases when api call is successful`() {
        // Arrange
        login()
        val mockPurchase = mockk<Purchase>()
        val groupId = mockGroup.id
        coEvery {
            mainRepository.createPurchase(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(Unit)
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(listOf(mockk())))
        groupViewModel.state.value = ApiResult.Success(mockGroup)

        // Act
        groupViewModel.createPurchase(mockPurchase)

        // Assert
        coVerify { mainRepository.getGroupPurchases(any(), groupId, any()) }
    }

    @Test
    fun `createPurchase should set state to error when api result is error`() {
        // Arrange
        login()
        val mockPurchase = mockk<Purchase>()

        coEvery {
            mainRepository.createPurchase(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Error("purchase total price error")

        groupViewModel.state.value = ApiResult.Success(mockGroup)

        // Act
        groupViewModel.createPurchase(mockPurchase)

        // Assert
        assertEquals(groupViewModel.newPurchaseState.value.status, ApiStatus.ERROR)
        assertEquals(groupViewModel.newPurchaseState.value.message, "purchase total price error")
    }

    @Test
    fun `addMember should call fetchGroupData when api call is successful`() {
        // Arrange
        login()
        val email = "test@test.com"
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(listOf(mockk())))
        coEvery {
            mainRepository.addUserToGroup(
                any(),
                email,
                any()
            )
        } returns ApiResult.Success(Unit)
        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        groupViewModel.state.value = ApiResult.Success(mockGroup)
        // Act

        groupViewModel.addMember(email)

        // Assert
        coVerify { mainRepository.getGroupWithMembers(any(), mockGroup.id) }
    }

    @Test
    fun `addMember should update newMemberState when api call is successful`() {
        //Arrange
        login()
        val email = "test@test.com"
        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        coEvery {
            mainRepository.getGroupPurchases(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(flowOf(listOf(mockk())))
        coEvery {
            mainRepository.addUserToGroup(
                any(),
                email,
                any()
            )
        } returns ApiResult.Success(Unit)

        // Act
        groupViewModel.state.value = ApiResult.Success(mockGroup)
        groupViewModel.addMember(email)

        // Assert
        assert(groupViewModel.newMemberState.value == ApiResult.Success(Unit))
    }

    @Test
    fun `addMember should update newMemberState when api result is error`() {
        //Arrange
        login()
        val email = "test@test.com"
        coEvery {
            mainRepository.addUserToGroup(
                any(),
                email,
                any()
            )
        } returns ApiResult.Error("user not found")

        // Act
        groupViewModel.state.value = ApiResult.Success(mockGroup)
        groupViewModel.addMember(email)

        // Assert
        assertEquals(groupViewModel.newMemberState.value.status, ApiStatus.ERROR)
        assertEquals(groupViewModel.newMemberState.value.message, "user not found")
    }



    @Test
    fun `newMemberReset should set newMemberState to empty`() {
        //Arrange
        groupViewModel.newMemberState.value = ApiResult.Success(Unit)

        // Act
        groupViewModel.newMemberReset()

        // Assert
        assertEquals(groupViewModel.newMemberState.value.status, ApiStatus.EMPTY)
    }


    @Test
    fun `newPurchaseReset should set newPurchaseState to empty`() {
        //Arrange
        groupViewModel.newPurchaseState.value = ApiResult.Success(Unit)

        // Act
        groupViewModel.newPurchaseReset()

        // Assert
        assertEquals(groupViewModel.newPurchaseState.value.status, ApiStatus.EMPTY)
    }


    @Test
    fun `leaveGroupReset should set leaveGroupState to empty`() {
        //Arrange
        groupViewModel.leaveGroupState.value = ApiResult.Success(Unit)

        // Act
        groupViewModel.leaveStateReset()

        // Assert
        assertEquals(groupViewModel.leaveGroupState.value.status, ApiStatus.EMPTY)
    }
}
