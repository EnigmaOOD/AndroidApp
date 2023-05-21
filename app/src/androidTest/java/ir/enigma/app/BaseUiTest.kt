package ir.enigma.app

import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.mockk
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.navigation.Screen
import org.junit.Rule

abstract class BaseUiTest(val startDestination: String) {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    lateinit var userRepository: UserRepository
    lateinit var mainRepository: MainRepository

    open fun setUp() {
        // Arrange of all tests in this class
        userRepository = mockk()
        mainRepository = mockk()

    }

    fun setComposeTestRule() {
        composeTestRule.setContent {
            RtlThemePreview {
                TestNavHost(
                    mockMainRepository = mainRepository,
                    mockUserRepository = userRepository,
                    startDestination = startDestination
                )
            }
        }
    }
}