package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.repostitory.UserRepository.Companion.EMAIL_EXIST
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class AddGroupTest : BaseUiTest(Screen.MainScreen.name) {
    // define all local Semantic nodes in testRegisterAndLogin here
    lateinit var tfGrpName: SemanticsNodeInteraction
    lateinit var tfGrpNameError: SemanticsNodeInteraction
    lateinit var tfGrpCurrency: SemanticsNodeInteraction
    lateinit var tfGrpCurrencyError: SemanticsNodeInteraction
    lateinit var btnAddEmailField: SemanticsNodeInteraction
    lateinit var btnAddGroupButton: SemanticsNodeInteraction
    lateinit var snackbar: SemanticsNodeInteraction

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        initMeAndToken()
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Success(flowOf(emptyList()))
        setComposeTestRule()
        composeTestRule.onNodeWithTag("addGroupButton").performClick()
        tfGrpName = composeTestRule.onNodeWithTag("groupNameTextField").onChildren()[0]
        tfGrpNameError = tfGrpName.onSibling()
        tfGrpCurrency = composeTestRule.onNodeWithTag("groupCurrencyTextField").onChildren()[0]
        tfGrpCurrencyError = tfGrpCurrency.onSibling()
        btnAddEmailField = composeTestRule.onNodeWithTag("addEmailField")
        btnAddGroupButton = composeTestRule.onNodeWithTag("addGroupButton")
        snackbar = composeTestRule.onNodeWithTag("snackbar")
    }

    @Test
    fun add_group_should_display_errors_when_empty_fields() {

        //Act: fill text fields with empty
        tfGrpName.performTextInput("")
        tfGrpCurrency.performTextInput("")
        btnAddGroupButton.performClick()

        //Assert: errors should be displayed
        tfGrpNameError.assert(hasText("نام گروه نمی\u200Cتواند خالی باشد"))
        tfGrpCurrencyError.assert(hasText("واحد پول نمی\u200Cتواند خالی باشد"))

    }

    @Test
    fun click_on_add_button_and_add_email_text_field() {

        //Act: click on add button while there is no email field
        btnAddEmailField.performClick()

        //Assert: new email field should be display and equal to "one"
        composeTestRule.onAllNodesWithTag("emailField").assertCountEquals(1)

    }

    @Test
    fun add_group_should_navigate_to_main_when_valid_input_fields_and_api_result_is_success() {

        //Arrange: mock api success result
        coEvery { mainRepository.createGroup(any(), any()) } returns ApiResult.Success(Unit)

        //Act: click on submit button when text fields are empty
        setInputFields()
        btnAddGroupButton.performClick()

        //Assert: navigate to main
        composeTestRule.onNodeWithTag("MainTopBar").assertIsDisplayed()

    }

    @Test
    fun add_group_should_show_error_when_unvalid_email_field_and_api_result_is_error() {

        //Arrange: mock api success result
        coEvery { mainRepository.createGroup(any(), any()) } returns ApiResult.Error(EMAIL_EXIST)

        //Act: click on submit button when text fields are empty
        setInputFields()
        btnAddGroupButton.performClick()

        //Assert: navigate to main
        snackbar.assertIsDisplayed()
        composeTestRule.onNodeWithText(EMAIL_EXIST).assertIsDisplayed()

    }

    private fun setInputFields() {
        tfGrpName.performTextInput("nameTest")
        tfGrpCurrency.performTextInput("currencyTest")
        btnAddEmailField.performClick()
        composeTestRule.onAllNodesWithTag("emailField")[0].onChildren()[0].performTextInput("test@gmail.com")
    }

}