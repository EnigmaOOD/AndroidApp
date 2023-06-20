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
    fun `getGroupWithMembers should return correct group data when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.getAGroup(any(), any()) } returns Response.success(mockGroupWithoutMember)
            coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
                listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
            )

            //Act: call repository function
            val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            assertEquals(response.data?.members!![1].user, mockUser2)
            assertEquals(response.data?.name, "test1")

        }

    @Test
    fun `getGroupWithMembers should return error when api getAGroup return 400 error`() =
        runBlocking {

            //Arrange: mock api function error result
            coEvery { api.getAGroup(any(), any()) } returns Response.error(400, "".toResponseBody())
            coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
                listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
            )

            //Act: call repository function
            val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

            //Assert: response should be error
            assertEquals(response.status, ApiStatus.ERROR)

        }

    @Test
    fun `getGroupWithMembers should return error when api getGroupMembers return 400 error`() =
        runBlocking {

            //Arrange: mock api function error result
            coEvery { api.getAGroup(any(), any()) } returns Response.error(400, "".toResponseBody())
            coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
                listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
            )

            //Act: call repository function
            val response = mainRepository.getGroupWithMembers(token = "test", groupId = 0)

            //Assert: response should be error
            assertEquals(response.status, ApiStatus.ERROR)

        }

    @Test
    fun `getGroups should return correct list of groups data when is not null`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.getGroups(any()) } returns Response.success(
            GroupList(
                listOf(
                    mockGroup1,
                    mockGroup2
                )
            )
        )

        //Act: call repository function
        val response = mainRepository.getGroups(token = "test")

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        response.data!!.distinctUntilChanged().collect {
            assertEquals(listOf(mockGroup1, mockGroup2), it)
        }

    }

    @Test
    fun `getGroups should return empty list of groups when not found any groups`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.getGroups(any()) } returns Response.success(GroupList(emptyList()))

        //Act: call repository function
        val response = mainRepository.getGroups(token = "test")

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data!!.toList(), listOf(emptyList<Group>()))

    }

    @Test
    fun `getGroups should return exception in other situation`() = runBlocking {

        //Arrange: mock api function throw exception result
        coEvery { api.getGroups(any()) } throws Exception("اتصال به اینترنت برقرار نیست")

        //Act: call repository function
        val response = mainRepository.getGroups(token = "test")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response, ApiResult.Error("اتصال به اینترنت برقرار نیست"))

    }

    @Test
    fun `getGroups should return suitable error when api result 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.getGroups(any()) } returns Response.error(400, "".toResponseBody())

        //Act: call repository function
        val response = mainRepository.getGroups("test")

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response, ApiResult.Error("با عرض پوزش خطایی غیر منتظره رخ داده است."))

    }


    @Test
    fun `getGroupPurchases should return correct purchases data based on oldest when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.getGroupPurchases(any(), any()) } returns Response.success(
                listOf(
                    mockPurchase1,
                    mockPurchase2
                )
            )

            //Act: call repository function
            val response = mainRepository.getGroupPurchases("test", 0, 0)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }

        }

    @Test
    fun `getGroupPurchases should return correct purchases data based on newest when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.getGroupPurchases(any(), any()) } returns Response.success(
                listOf(
                    mockPurchase1,
                    mockPurchase2
                )
            )

            //Act: call repository function
            val response = mainRepository.getGroupPurchases("test", 0, 1)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }

        }

    @Test
    fun `getGroupPurchases should return correct purchases data based on most expensive when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.filterBaseDecrease(any(), any()) } returns Response.success(
                listOf(
                    mockPurchase1,
                    mockPurchase2
                )
            )

            //Act: call repository function
            val response = mainRepository.getGroupPurchases("test", 0, 3)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }

        }

    @Test
    fun `getGroupPurchases should return correct purchases data based on cheapest when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.filterBaseDecrease(any(), any()) } returns Response.success(
                listOf(
                    mockPurchase1,
                    mockPurchase2
                )
            )

            //Act: call repository function
            val response = mainRepository.getGroupPurchases("test", 0, 4)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }

        }

    @Test
    fun `getGroupPurchases should return correct purchases data based on your purchases when api result is success`() =
        runBlocking {

            //Arrange: mock api function success result
            coEvery { api.filterByMe(any(), any()) } returns Response.success(
                Api.UserGroupBuysResponse(
                    listOf(
                        mockPurchase1,
                        mockPurchase2
                    )
                )
            )

            //Act: call repository function
            val response = mainRepository.getGroupPurchases("test", 0, 2)

            //Assert: response should be success with correct data
            assertEquals(response.status, ApiStatus.SUCCESS)
            response.data!!.distinctUntilChanged().collect {
                assertEquals(listOf(mockPurchase1, mockPurchase2), it)
            }

        }

    @Test
    fun `getGroupPurchases should return error when api result 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.getGroupPurchases(any(), any()) } returns Response.error(
            400,
            "".toResponseBody()
        )
        coEvery { api.filterBaseDecrease(any(), any()) } returns Response.error(
            400,
            "".toResponseBody()
        )
        coEvery { api.filterByMe(any(), any()) } returns Response.error(400, "".toResponseBody())

        //Act: call repository function
        val response = mainRepository.getGroupPurchases("test", 0)

        //Assert: response should be error with correct massage
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "خطا در دریافت خرید ها")

    }


    @Test
    fun `createPurchase should be successful when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.createPurchase(any(), any()) } returns Response.success(null)

        //Act: call repository function
        val response = mainRepository.createPurchase("test", 0, mockPurchase1)

        //Assert: response should be success
        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `createPurchase should be failed when api result is 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.createPurchase(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.createPurchase("test", 0, mockPurchase1)

        //Assert: response should be error
        assertEquals(response.status, ApiStatus.ERROR)

    }


    @Test
    fun `leaveGroup should be successful when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.leaveGroup(any(), any()) } returns Response.success(null)

        //Act: call repository function
        val response = mainRepository.leaveGroup("test", 0)

        //Assert: response should be success
        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `leaveGroup should failed when api result is 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.leaveGroup(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.leaveGroup("test", 0)

        //Assert: response should be error
        assertEquals(response.status, ApiStatus.ERROR)

    }

    @Test
    fun `leaveGroup should failed and set not saddle up message when api result is 401 error`() =
        runBlocking {

            //Arrange: mock api function error result
            coEvery { api.leaveGroup(any(), any()) } returns Response.error(
                401,
                "تسویه حساب نشده است".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
            )

            //Act: call repository function
            val response = mainRepository.leaveGroup("test", 0)

            //Assert: response should be error and with correct massage
            assertEquals(response.status, ApiStatus.ERROR)
            assertEquals(response.message, "تسویه حساب نشده است")

        }


    @Test
    fun `getGroupToAmount should return amount of user when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.getGroupMembers(any(), any()) } returns Response.success(
            listOf(Member(mockUser1, 0.0), Member(mockUser2, 0.0))
        )

        //Act: call repository function
        val response = mainRepository.getGroupToAmount("test", 0, 1)

        //Assert: response should be success with correct data
        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data, 0.0)

    }

    @Test
    fun `getGroupToAmount should return error when api result 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.getGroupMembers(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.getGroupToAmount("test", 0, 1)

        //Assert: response should be error
        assertEquals(response.status, ApiStatus.ERROR)

    }


    @Test
    fun `createGroup should be successful when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.createGroup(any(), any()) } returns Response.success(null)

        //Act: call repository function
        val response = mainRepository.createGroup("test", mockAddGroup)

        //Assert: response should be success
        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `createGroup should return suitable error when api result 404 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.createGroup(any(), any()) } returns Response.error(
            404,
            "ایمیل اعضا معتبر نمی باشد.".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.createGroup("test", mockAddGroup)

        //Assert: response should be error with correct message
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "ایمیل اعضا معتبر نمی باشد.")

    }


    @Test
    fun `addUserToGroup should be successful when api result is success`() = runBlocking {

        //Arrange: mock api function success result
        coEvery { api.addUserToGroup(any(), any()) } returns Response.success(null)

        //Act: call repository function
        val response = mainRepository.addUserToGroup("test", "test@test.com", 0)

        //Assert: response should be success
        assertEquals(response.status, ApiStatus.SUCCESS)

    }

    @Test
    fun `addUserToGroup should not valid email when api result is 404 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.addUserToGroup(any(), any()) } returns Response.error(
            404,
            "ایمیل اعضا معتبر نمی باشد.".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.addUserToGroup("test", "test", 0)

        //Assert: response should be error with correct massage
        assertEquals(response.status, ApiStatus.ERROR)
        assertEquals(response.message, "ایمیل اعضا معتبر نمی باشد.")

    }

    @Test
    fun `addUserToGroup should return error when api result 400 error`() = runBlocking {

        //Arrange: mock api function error result
        coEvery { api.addUserToGroup(any(), any()) } returns Response.error(
            400,
            "".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )

        //Act: call repository function
        val response = mainRepository.addUserToGroup("test", "test", 0)

        //Assert: response should be error
        assertEquals(response.status, ApiStatus.ERROR)

    }


    companion object {
        val mockGroupWithoutMember =
            Group.Builder()
                .id(1)
                .name("test1")
                .categoryId(2)
                .currency("test")
                .build()

        val mockGroup1 =
            Group.Builder()
                .id(1)
                .name("test1")
                .categoryId(2)
                .currency("test")
                .members(listOf(Member(mockUser1, 2000.0), Member(mockUser2, 1000.0)))
                .build()

        val mockGroup2 =
            Group.Builder()
                .id(2)
                .name("test2")
                .categoryId(2)
                .currency("test")
                .members(listOf(Member(mockUser1, 4000.0), Member(mockUser2, 3000.0)))
                .build()

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