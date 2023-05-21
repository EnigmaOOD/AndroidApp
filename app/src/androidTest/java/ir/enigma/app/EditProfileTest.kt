package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.data.ApiResult
import ir.enigma.app.data.ApiStatus
import ir.enigma.app.model.Token
import ir.enigma.app.repostitory.UserRepository
import ir.enigma.app.ui.navigation.Screen
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class EditProfileTest : BaseUiTest(Screen.MainScreen.name) {

    // define all local Semantic nodes in testRegisterAndLogin here
    lateinit var tfName: SemanticsNodeInteraction
    lateinit var tfNameError: SemanticsNodeInteraction
    lateinit var btnEdit: SemanticsNodeInteraction
    lateinit var btnExit: SemanticsNodeInteraction
    lateinit var characterCard: SemanticsNodeInteraction

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Success(flowOf(emptyList()))
        initMeAndToken()
        setComposeTestRule()
        composeTestRule.onNodeWithTag("User Avatar").performClick()
        tfName = composeTestRule.onNodeWithTag("nameTextField").onChildren()[0]
        btnEdit = composeTestRule.onNodeWithTag("editButton")
        btnExit = composeTestRule.onNodeWithTag("exitButton")
        characterCard = composeTestRule.onNodeWithTag("characterCard")
    }

    @Test
    fun empty_name_field_should_display_error() {

        //Act: empty value of edit text field
        tfName.performTextInput("")

        //Assert: error should be equal to suitable text
        composeTestRule.onNodeWithText("نام و نام خانوادگی نمی\u200Cتواند خالی باشد")

    }

    @Test
    fun edit_name_field_when_change_name_field() {

        //Arrange: mock api success result
        coEvery { userRepository.editProfile(any(), any(), any()) } returns ApiResult.Success(Unit)

        //Act: click on edit text field and change its value and then click on edit button
        tfName.performTextInput("editTestName")
        btnEdit.performClick()


        //Assert:  pop back stack
        composeTestRule.onNodeWithTag("MainTopBar").assertIsDisplayed()


    }

    @Test
    fun click_on_character_card_and_should_show_character_dialog() {

        //Act: click on character card
        characterCard.performClick()

        //Assert: character dialog should be displayed
        composeTestRule.onNodeWithTag("characterDialog").assertIsDisplayed()

    }

    @Test
    fun click_on_character_card_and_change_character_and_should_changed_character() {

        //Act: click on character card
        characterCard.performClick()
        composeTestRule.onAllNodesWithTag("charactersList")[5].performClick()

        //Assert: character dialog should be displayed
        composeTestRule.onNodeWithTag("characterDialog").assertDoesNotExist()

    }

    @Test
    fun exit_account_should_be_success_and_navigate_to_login_screen() {

        //Act: empty value of edit text field
        btnExit.performClick()

        //Assert: error should be equal to suitable text
        composeTestRule.onNodeWithTag("loginToggle").assertIsDisplayed()

    }
}