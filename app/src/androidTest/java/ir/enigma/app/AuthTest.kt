package ir.enigma.app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Token
import ir.enigma.app.model.User
import ir.enigma.app.model.UserInfo
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.repostitory.UserRepository.Companion.EMAIL_EXIST
import ir.enigma.app.ui.auth.AuthViewModel.Companion.EMAIL_VERIFICATION
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AuthTest : BaseUiTest(Screen.AuthScreen.name) {

    // define all local Semantic nodes in testRegisterAndLogin here
    lateinit var tfName: SemanticsNodeInteraction
    lateinit var tfNameError: SemanticsNodeInteraction
    lateinit var tfEmail: SemanticsNodeInteraction
    lateinit var tfEmailError: SemanticsNodeInteraction
    lateinit var tfPassword: SemanticsNodeInteraction
    lateinit var tfPasswordError: SemanticsNodeInteraction
    lateinit var btnLoginToggle: SemanticsNodeInteraction
    lateinit var btnSubmit: SemanticsNodeInteraction
    lateinit var snackbar: SemanticsNodeInteraction


    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        setComposeTestRule()
        tfName = composeTestRule.onNodeWithTag("nameTextField").onChildren()[0]
        tfNameError = tfName.onSibling()
        tfEmail = composeTestRule.onNodeWithTag("emailTextField").onChildren()[0]
        tfEmailError = tfEmail.onSibling()
        tfPassword = composeTestRule.onNodeWithTag("passwordTextField").onChildren()[0]
        tfPasswordError = tfPassword.onSibling()
        btnLoginToggle = composeTestRule.onNodeWithTag("loginToggle")
        btnSubmit = composeTestRule.onNodeWithTag("submitButton")
        snackbar = composeTestRule.onNodeWithTag("snackbar")
    }

    @Test
    fun authScreen_register_should_display_errors_when_empty_fields() {

        //Act: click on submit button when text fields are empty
        btnSubmit.performClick()

        //Assert: errors should be displayed
        tfNameError.assertTextEquals("نام و نام خانوادگی نمی\u200Cتواند خالی باشد")
        tfPasswordError.assertTextEquals("رمز عبور باید حداقل ۸ کاراکتر باشد")
        tfEmailError.assertTextEquals("فرمت ایمیل صحیح نیست")

    }


    @Test
    fun authScreen_register_should_invisible_all_displayed_errors_when_valid_inputs() {
        //Arrange: click on submit button when fields is empty to show errors
        btnSubmit.performClick()

        //Act: enter valid inputs
        setInputFields()

        //Assert: errors should be gone
        tfNameError.assertTextEquals("")
        tfPasswordError.assertTextEquals("")
        tfEmailError.assertTextEquals("")
    }

    @Test
    fun authScreen_toggle_register_to_login_should_set_page_to_login_state() {
        // Test for register text
        //Assert: text should be register first
        btnSubmit.assertTextEquals("ثبت\u200Cنام")

        //Act: click on toggle button
        btnLoginToggle.performClick()

        //Assert: text should be changed to login
        btnSubmit.assertTextEquals("ورود")

    }

    @Test
    fun authScreen_register_should_show_error_snackbar_when_valid_input_fields_and_api_result_is_error() {
        //Test for register
        //Arrange: mock api error result
        coEvery { userRepository.register(any()) } returns ApiResult.Error(EMAIL_EXIST)


        //Act: click on submit button should show EMAIL_VERIFICATION snackbar when api result is success
        setInputFields()
        btnSubmit.performClick()

        //Assert: EMAIL_EXIST Error snackbar should be displayed
        snackbar.assertIsDisplayed()
        composeTestRule.onNodeWithText(EMAIL_EXIST).assertIsDisplayed()

    }

    @Test
    fun authScreen_register_should_show_email_verification_snackbar_when_valid_input_fields_and_api_result_is_success() {
        //Arrange: mock api success result
        coEvery { userRepository.register(any()) } returns ApiResult.Success(mockUser1)

        //Act: click on submit button should show EMAIL_VERIFICATION snackbar when api result is success
        setInputFields()
        btnSubmit.performClick()

        //Assert: EMAIL_VERIFICATION snackbar should be displayed
        snackbar.assertIsDisplayed()
        composeTestRule.onNodeWithText(EMAIL_VERIFICATION).assertIsDisplayed()
    }

    @Test
    fun authScreen_login_should_navigate_to_main_when_valid_input_fields_and_api_result_is_success() {
        //Arrange: mock api success result
        coEvery { userRepository.login(any(), any()) } returns ApiResult.Success(Token("token"))
        coEvery { userRepository.getUserInfo(any()) } returns ApiResult.Success(UserInfo(mockUser1))
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Success(flowOf(emptyList()))


        //Act: click on submit button should navigate to main when api result is success
        setInputFields()
        btnLoginToggle.performClick()
        btnSubmit.performClick()

        //Assert: main screen should be displayed
        composeTestRule.onNodeWithTag("MainTopBar").assertIsDisplayed()
    }

    //---------------------------------------------------------------------------tools
    private fun setInputFields() {
        tfName.performTextInput("test")
        tfEmail.performTextInput("a@a.com")
        tfPassword.performTextInput("12345678")
    }


}