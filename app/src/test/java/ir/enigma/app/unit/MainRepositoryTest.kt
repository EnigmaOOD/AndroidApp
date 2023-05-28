package ir.enigma.app.unit

import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.*
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.network.Api
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.MainRepository.Companion.NO_GROUP
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockLog
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockUser1
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockUser2
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MainRepositoryTest {
    private lateinit var api: Api
    private lateinit var mainRepository: MainRepository

    @Before
    fun setup() {
        mockLog()
        api = mockk()
        mainRepository = MainRepository(api)
    }

    @Test
    fun `getGroupWithMembers should give correct group data`() = runBlocking {

        coEvery { api.getAGroup(any(), any()) } returns Response.success(mockGroupWithoutMember)
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data?.members!![1].user, mockUser2)
        assertEquals(response.data?.name, "test1")

    }

    @Test
    fun `getGroupWithMembers should give error when api getAGroup return error`() = runBlocking {

        coEvery { api.getAGroup(any(), any()) } returns Response.error(400 , "".toResponseBody())
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

        assertEquals(response.status, ApiStatus.ERROR)

    }

    @Test
    fun `getGroupWithMembers should give error when api getGroupMembers return error`() = runBlocking {

        coEvery { api.getAGroup(any(), any()) } returns Response.error(400 , "".toResponseBody())
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

        assertEquals(response.status, ApiStatus.ERROR)

    }

    @Test
    fun `getGroups should give correct list of groups data if is not null`() = runBlocking {

        coEvery { api.getGroups(any()) } returns Response.success(
            GroupList(
                listOf(
                    mockGroup1,
                    mockGroup2
                )
            )
        )

        val response = mainRepository.getGroups(token = "test")

        assertEquals(response.status, ApiStatus.SUCCESS)
        response.data!!.distinctUntilChanged().collect {
            assertEquals(listOf(mockGroup1, mockGroup2), it)
        }

    }

    @Test
    fun `getGroups should give empty list of groups if not found any groups`() = runBlocking {

        coEvery { api.getGroups(any()) } returns Response.error(404 , "".toResponseBody())

        val response = mainRepository.getGroups(token = "test")

        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data!!.toList(), listOf(emptyList<Group>()))

    }

    @Test
    fun `getGroups should give exception in other situation`() = runBlocking {
        coEvery { api.getGroups(any()) } throws Exception("اتصال به اینترنت برقرار نیست")

        val response = mainRepository.getGroups(token = "test")

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response, ApiResult.Error("اتصال به اینترنت برقرار نیست"))
    }

    @Test
    fun `getGroups should give error when api result error`() = runBlocking{
        coEvery { api.getGroups(any()) } returns Response.error(400 , "".toResponseBody())

        val response = mainRepository.getGroups("test")

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response, ApiResult.Error("با عرض پوزش خطایی غیر منتظره رخ داده است."))
    }


    @Test
    fun `getGroupPurchases should give correct purchases data based on oldest`() = runBlocking {
        coEvery { api.getGroupPurchases(any(), any()) } returns Response.success(
            listOf(
                mockPurchase1,
                mockPurchase2
            )
        )

        val response = mainRepository.getGroupPurchases("test", 0, 0)

        assertEquals(response.status, ApiStatus.SUCCESS)
        response.data!!.distinctUntilChanged().collect {
            assertEquals(listOf(mockPurchase1, mockPurchase2), it)
        }
    }

    @Test
    fun `getGroupPurchases should give correct purchases data based on newest`() = runBlocking {
        coEvery { api.getGroupPurchases(any(), any()) } returns Response.success(
            listOf(
                mockPurchase1,
                mockPurchase2
            )
        )

        val response = mainRepository.getGroupPurchases("test", 0, 1)

        assertEquals(response.status, ApiStatus.SUCCESS)
        response.data!!.distinctUntilChanged().collect {
            assertEquals(listOf(mockPurchase1, mockPurchase2), it)
        }
    }

    @Test
    fun `getGroupPurchases should give correct purchases data based on most expensive`() =
        runBlocking {
            coEvery { api.filterBaseDecrease(any(), any()) } returns Response.success(
                listOf(
                    mockPurchase1,
                    mockPurchase2
                )
            )

            val response = mainRepository.getGroupPurchases("test", 0, 3)

            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }
        }

    @Test
    fun `getGroupPurchases should give correct purchases data based on cheapest`() = runBlocking {
        coEvery { api.filterBaseDecrease(any(), any()) } returns Response.success(
            listOf(
                mockPurchase1,
                mockPurchase2
            )
        )

        val response = mainRepository.getGroupPurchases("test", 0, 4)

        assertEquals(response.status, ApiStatus.SUCCESS)
        response.data!!.distinctUntilChanged().collect {
            assertEquals(listOf(mockPurchase1, mockPurchase2), it)
        }
    }

    @Test
    fun `getGroupPurchases should give correct purchases data based on your purchases`() =
        runBlocking {
            coEvery { api.filterByMe(any(), any()) } returns Response.success(
                Api.UserGroupBuysResponse(
                    listOf(
                        mockPurchase1,
                        mockPurchase2
                    )
                )
            )

            val response = mainRepository.getGroupPurchases("test", 0, 2)

            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }
        }

    @Test
    fun `getGroupPurchases should give error`() = runBlocking {
        coEvery { api.getGroupPurchases(any(), any()) } returns Response.error(
            400,
            "خطا در دریافت گروه ها".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
        coEvery { api.filterBaseDecrease(any(), any()) } returns Response.error(
            400,
            "خطا در دریافت گروه ها".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
        coEvery { api.filterByMe(any(), any()) } returns Response.error(
            400,
            "خطا در دریافت گروه ها".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.getGroupPurchases("test", 0)

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "خطا در دریافت خرید ها")
    }


    @Test
    fun `createPurchase should be successful when api result is success`() = runBlocking {
        coEvery { api.createPurchase(any(), any()) } returns Response.success(null)

        val response = mainRepository.createPurchase("test", 0, mockPurchase1)

        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `createPurchase should be failed when api result is error`() = runBlocking {

        coEvery { api.createPurchase(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.createPurchase("test", 0, mockPurchase1)

        assertEquals(response.status, ApiStatus.ERROR)
        
    }


    @Test
    fun `leaveGroup should be successful`() = runBlocking {
        coEvery { api.leaveGroup(any(), any()) } returns Response.success(null)

        val response = mainRepository.leaveGroup("test", 0)

        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `leaveGroup should failed when api result is error`() = runBlocking {
        coEvery { api.leaveGroup(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.leaveGroup("test", 0)

        assertEquals(response.status, ApiStatus.ERROR)
    }

    @Test
    fun `leaveGroup should failed and set not saddle up message when api result is 401 error`() = runBlocking {
        coEvery { api.leaveGroup(any(), any()) } returns Response.error(
            401,
            "تسویه حساب نشده است".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.leaveGroup("test", 0)

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "تسویه حساب نشده است")

    }


    @Test
    fun `getGroupToAmount should give amount of user`() = runBlocking {
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        val response = mainRepository.getGroupToAmount("test", 0, 1)

        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data, 0.0)

    }

    @Test
    fun `getGroupToAmount should give error`() = runBlocking {
        coEvery { api.getGroupMembers(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("".toMediaTypeOrNull())
        )

        val response = mainRepository.getGroupToAmount("test", 0, 1)

        assertEquals(response.status, ApiStatus.ERROR)

    }


    @Test
    fun `createGroup should be successful`() = runBlocking {
        coEvery { api.createGroup(any(), any()) } returns Response.success(null)

        val response = mainRepository.createGroup("test", mockAddGroup)

        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `createGroup should give error`() = runBlocking {
        coEvery { api.createGroup(any(), any()) } returns Response.error(
            404,
            "ایمیل اعضا معتبر نمی باشد.".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.createGroup("test", mockAddGroup)

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "ایمیل اعضا معتبر نمی باشد.")

    }


    @Test
    fun `addUserToGroup should be successful`() = runBlocking {
        coEvery { api.addUserToGroup(any(), any()) } returns Response.success(null)

        val response = mainRepository.addUserToGroup("test", "test@test.com", 0)

        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `addUserToGroup should not valid email when api result is 404 error`() = runBlocking {
        coEvery { api.addUserToGroup(any(), any()) } returns Response.error(
            404,
            "ایمیل اعضا معتبر نمی باشد.".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.addUserToGroup("test", "test" , 0)

        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "ایمیل اعضا معتبر نمی باشد.")
    }

    @Test
    fun `addUserToGroup should give error when api result error`() = runBlocking {
        coEvery { api.addUserToGroup(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        val response = mainRepository.addUserToGroup("test", "test", 0)

        assertEquals(response.status, ApiStatus.ERROR)

    }




    companion object {
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

        val mockAddGroup =
            AddGroupRequest(
                name = "test",
                picture_id = 0,
                currency = "test",
                emails = listOf("test1@a.com", "test2@a.com")
            )

    }
}