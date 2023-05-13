package ir.enigma.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.User
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.group.GroupViewModel
import ir.enigma.app.ui.main.MainViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ViewModulesTest {
    lateinit var api: MockApi
    lateinit var authViewModel: AuthViewModel
    lateinit var userRepository: UserRepository
    lateinit var mainRepository: MainRepository
    lateinit var mainViewModel: MainViewModel
    lateinit var groupViewModel: GroupViewModel
    lateinit var context: Context


    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext();
        api = MockApi()
        mainRepository = MainRepository(api = api)
        userRepository = UserRepository(api = api)
        authViewModel = AuthViewModel(userRepository = userRepository)
        mainViewModel = MainViewModel(mainRepository = mainRepository)
        groupViewModel = GroupViewModel(mainRepository = mainRepository)
    }


    @Test
    fun `test register`() {
        val user = User(0, "test", name = "test", 12, "test")
        authViewModel.register(context , user.name, user.email ,user.iconId , user.password!!)
        val token = authViewModel.state.value.data?.token
        assert(authViewModel.state.value is ApiResult.Success)
        assert(token == AuthViewModel.token)
        assert(AuthViewModel.me.email == user.email)
    }

    // token test
    @Test
    fun `test checkForToken`() {
        authViewModel.checkForToken(context)
        assert(AuthViewModel.me.email == "test")
    }



    @Test
    fun `test getGroups`() {
        mainViewModel.fetchGroups()
        assert(mainViewModel.groupList.value.isNotEmpty())
    }

    @Test
    fun `test getGroupToAmount`() {
        mainViewModel.fetchGroups()
        assert(mainViewModel.groupToAmount.isNotEmpty())
        assert(mainViewModel.groupToAmount[1] != null)
    }

    @Test
    fun `test getGroupWithMembers`() {
        mainViewModel.fetchGroups()
        assert(mainViewModel.groupList.value.isNotEmpty())
        mainViewModel.groupList.value.forEach {
            groupViewModel.fetchGroupData(it.id)
            assert(groupViewModel.purchaseList.value.isNotEmpty())
            assert(groupViewModel.meMember != null)
        }
    }


}
