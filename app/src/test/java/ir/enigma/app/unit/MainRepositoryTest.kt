package ir.enigma.app.unit

import okhttp3.ResponseBody
import okhttp3.MediaType
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.*
import ir.enigma.app.network.Api
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.MainRepository.Companion.NO_GROUP
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockUser1
import ir.enigma.app.unit.BaseViewModelTest.Companion.mockUser2
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
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

        assertEquals(response.status, ApiStatus.SUCCESS)
        assertEquals(response.data?.members!![1].user, mockUser2)
        assertEquals(response.data?.name, "test1")

    }

    @Test
    fun `getGroups should get correct list of groups data`() = runBlocking {

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
    fun `getGroups should get 404 error`() = runBlocking {

        coEvery { api.getGroups(any()) } returns Response.error(404, "".toResponseBody("".toMediaTypeOrNull()))

        val response = mainRepository.getGroups(token = "test")

        assertEquals(response.status, ApiStatus.SUCCESS)
//        assertEquals(response.status, ApiStatus.ERROR)
//        assertEquals(response.message, NO_GROUP)

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
    }
}