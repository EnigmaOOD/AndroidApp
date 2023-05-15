package ir.enigma.app

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.enigma.app.TestUtils.blockAMoment
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.group.GroupViewModel
import ir.enigma.app.ui.main.MainViewModel
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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
        authViewModel.register(context, user.name, user.email, user.iconId, user.password!!)

        val token = authViewModel.state.value.data?.token
        assert(authViewModel.state.value is ApiResult.Success)
        assert(token == AuthViewModel.token)
        assert(me.email == user.email)
    }
    

    @Test
    fun `test setMe`() {
        authViewModel.login(context, "test", "test")
        authViewModel.setMe()
        assert(me.email == "test")
    }


    @Test
    fun `test getGroups`() {
        authViewModel.login(context, "test", "test")
        mainViewModel.fetchGroups()
        blockAMoment()
        assert(mainViewModel.groupList.value.isNotEmpty())
        assert(mainViewModel.groupList.value[0].name == "test")
    }

    @Test
    fun `test getGroupToAmount`() {
        authViewModel.login(context, "test", "test")
        mainViewModel.fetchGroups()
        blockAMoment()
        assert(mainViewModel.groupList.value.isNotEmpty())
        mainViewModel.fetchGroupToAmountData(mainViewModel.groupList.value)
        blockAMoment()
        assert(mainViewModel.groupToAmount.isNotEmpty())
        assert(mainViewModel.groupToAmount[1]?.value == 2000.0)
    }

    @Test
    fun `test getGroupWithMembers`() {
        authViewModel.login(context, "test", "test")
        mainViewModel.fetchGroups()
        blockAMoment()
        assert(mainViewModel.groupList.value.isNotEmpty())
        mainViewModel.groupList.value.forEach {
            groupViewModel.fetchGroupData(it.id)
            blockAMoment()
            groupViewModel.fetchPurchases(it.id)
            blockAMoment()
            assert(groupViewModel.purchaseList.value.isNotEmpty())
            assert(groupViewModel.meMember?.user == me)
        }
    }

    // fetch purchase test
    @Test
    fun `test fetchPurchase`() {
        authViewModel.login(context, "test", "test")
        mainViewModel.fetchGroups()
        blockAMoment()
        assert(mainViewModel.groupList.value.isNotEmpty())
        mainViewModel.groupList.value.forEach {
            groupViewModel.fetchGroupData(it.id)
            blockAMoment()
            groupViewModel.fetchPurchases(it.id)
            blockAMoment()
            assert(groupViewModel.purchaseList.value.isNotEmpty())
        }
    }

    // create purchase test
    @Test
    fun `test createPurchase`() {
        authViewModel.login(context, "test", "test")
        mainViewModel.fetchGroups()
        blockAMoment()
        assert(mainViewModel.groupList.value.isNotEmpty())
        mainViewModel.groupList.value.forEach {
            groupViewModel.fetchGroupData(it.id)
            blockAMoment()
            groupViewModel.createPurchase(fakePurchase2)
            blockAMoment()
            assert(groupViewModel.newPurchaseState.value.status == ApiStatus.SUCCESS)
        }
    }

}

object TestUtils{
    const val DELAY = 600L
    fun blockAMoment(){
        runBlocking { delay(DELAY) }
    }
}
