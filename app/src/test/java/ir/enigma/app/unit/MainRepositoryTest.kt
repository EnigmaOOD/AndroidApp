package ir.enigma.app.unit

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.*
import ir.enigma.app.network.Api
import ir.enigma.app.repostitory.MainRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class MainRepositoryTest {
    private lateinit var api: Api
    private lateinit var mainRepository: MainRepository

    @Before
    fun setup() {
        api = mockk()
        mainRepository = MainRepository(api)
    }

    @Test
    fun `getGroupWithMembers should get correct group data`() = runBlocking {

        coEvery { api.getAGroup(any(), any()) } returns Response.success(mockGroupWithoutMember)
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

        assertEquals(response.status , ApiStatus.SUCCESS)
        assertEquals(response.data?.members!![1].user, mockUser2)
        assertEquals(response.data?.name , "test1")

    }

//    @Test
//    fun `getGroups should get correct list of groups data`() {
//
//        var result =
//            coEvery { api.getGroups(any()) } returns Response.success(GroupList(listOf(mockGroup1 , mockGroup2)))
//
//        val response = mainRepository.getGroupWithMembers(, )
//
//        assert(response == result)
//
//    }


    companion object {
        val mockUser1 = User(1, "test", "test", 2)
        val mockUser2 = User(2, "test2", "test2", 5)

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

        val mockGroupWithoutMember =
            Group(
                1,
                "test1",
                2,
                "test",
            )

        val mockGroup1 =
            Group(
                1,
                "test1",
                2,
                "test",
                listOf(Member(mockUser1, 2000.0), Member(mockUser2, 1000.0))
            )

        val mockGroup2 =
            Group(
                2,
                "test2",
                2,
                "test",
                listOf(Member(mockUser1, 4000.0), Member(mockUser2, 3000.0))
            )
    }
}