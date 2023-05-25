package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class MainTest : BaseUiTest(Screen.MainScreen.name) {
    // define all local Semantic nodes in testRegisterAndLogin here
    lateinit var btnAddGroup: SemanticsNodeInteraction
    lateinit var profileCharacterCard: SemanticsNodeInteraction

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        initMeAndToken()
        coEvery { mainRepository.getGroups(any()) } returns ApiResult.Success(
            flowOf(listOf(mockGroup))
        )
        coEvery { mainRepository.getGroupToAmount(any(), any(), any()) } returns ApiResult.Success(
            0.0
        )
        setComposeTestRule()
        btnAddGroup = composeTestRule.onNodeWithTag("addGroupButton")
        profileCharacterCard = composeTestRule.onNodeWithTag("profileCharacterCard")
    }

    @Test
    fun click_on_character_card_should_navigate_to_editProfile_screen() {

        //Act: click on profile character card
        profileCharacterCard.performClick()

        //Assert: navigate to editProfile screen
        composeTestRule.onNodeWithTag("editButton").assertIsDisplayed()

    }

    @Test
    fun click_on_add_group_button_should_navigate_to_addGroup_screen() {

        //Act: click on add group button
        btnAddGroup.performClick()

        //Assert: navigate to addGroup screen
        composeTestRule.onNodeWithTag("addGroupButton").assertIsDisplayed()

    }

    @Test
    fun number_of_groups_on_screen_should_be_equal_to_number_of_mock_groups() {

        //Assert: navigate to addGroup screen
        composeTestRule.onAllNodesWithTag("group").assertCountEquals(1)
        composeTestRule.onNodeWithTag("groupName", useUnmergedTree = true).assertExists(mockGroup.name)
        composeTestRule.onNodeWithTag("groupCurrency", useUnmergedTree = true).assertExists(mockGroup.currency)

    }
}