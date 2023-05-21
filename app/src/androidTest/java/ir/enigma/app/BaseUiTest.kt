package ir.enigma.app

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.mockk.mockk
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.model.*
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.auth.AuthViewModel
import ir.enigma.app.ui.navigation.Screen
import org.junit.Rule

abstract class BaseUiTest(val startDestination: String) {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    lateinit var navController: NavHostController
    lateinit var userRepository: UserRepository
    lateinit var mainRepository: MainRepository

    open fun setUp() {
        // Arrange of all tests in this class
        userRepository = mockk()
        mainRepository = mockk()

    }

    fun initMeAndToken(){
        AuthViewModel.me = mockUser1
        AuthViewModel.token = "token"
    }

    fun setComposeTestRule() {
        composeTestRule.setContent {
            navController = rememberNavController()
            RtlThemePreview {
                TestNavHost(
                    navController = navController,
                    mockMainRepository = mainRepository,
                    mockUserRepository = userRepository,
                    startDestination = startDestination
                )
            }
        }
    }


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

        val mockGroup =
            Group(
                1,
                "test",
                2,
                "test",
                listOf(Member(mockUser1, 2000.0), Member(mockUser2, 1000.0))
            )
    }
}