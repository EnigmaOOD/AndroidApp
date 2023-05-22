package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GroupMembersTest : BaseUiTest(Screen.GroupScreen.name) {

    lateinit var addMemberButton: SemanticsNodeInteraction
    lateinit var tfEmail: SemanticsNodeInteraction
    lateinit var tfEmailError: SemanticsNodeInteraction


    @Before
    override fun setUp() {

        //Arrange
        initMeAndToken()
        super.setUp()
        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        coEvery { mainRepository.getGroupPurchases(any(), any(), any()) } returns ApiResult.Success(
            flowOf(
                emptyList()
            )
        )
        setComposeTestRule()
        composeTestRule.onNodeWithTag("topAppBar").performClick()
        addMemberButton = composeTestRule.onNodeWithTag("addMemberButton")
        tfEmail = composeTestRule.onNodeWithTag("addMemberEmail").onChildren()[0]
        tfEmailError = tfEmail.onSibling()
    }

    @Test
    fun GroupMembersScreen_should_display_add_member_dialog_when_add_member_button_clicked() {

        //Act: click on add member button
        addMemberButton.performClick()

        //Assert: add member dialog should be displayed
        composeTestRule.onNodeWithTag("addMemberDialog").assertIsDisplayed()

    }

    @Test
    fun AddMemberDialog_should_show_error_when_api_add_member_has_error() {

        //Arrange: set api result to error
        coEvery {
            mainRepository.addUserToGroup(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Error("کاربر با این ایمیل پیدا نشد")
        // show add member dialog
        addMemberButton.performClick()


        //Act: fill tfEmail and click on add member button
        tfEmail.performTextInput("test@test.com")
        composeTestRule.onNodeWithTag("addMemberConfirm").performClick()

        //Assert: add member dialog should be displayed
        composeTestRule.onNodeWithTag("addMemberDialog").assertIsDisplayed()
        //Assert: error should be displayed
        tfEmailError.assertIsDisplayed().assertTextEquals("کاربر با این ایمیل پیدا نشد")

    }


    @Test
    fun AddMemberDialog_should_gone_when_cancel_button_clicked() {

        //Arrange:
        // show add member dialog
        addMemberButton.performClick()

        //Act: click on cancel button
        composeTestRule.onNodeWithTag("addMemberCancel").performClick()

        //Assert: add member dialog should be gone
        composeTestRule.onNodeWithTag("addMemberDialog").assertDoesNotExist()

    }

    @Test
    fun AddMemberDialog_should_gone_when_api_add_member_has_success() {

        //Arrange: set api result to success
        coEvery {
            mainRepository.addUserToGroup(
                any(),
                any(),
                any()
            )
        } returns ApiResult.Success(Unit)
        // show add member dialog
        addMemberButton.performClick()

        //Act: fill tfEmail and click on add member button
        tfEmail.performTextInput("test@test.com")
        composeTestRule.onNodeWithTag("addMemberConfirm").performClick()

        //Assert: add member dialog should be gone
        composeTestRule.onNodeWithTag("addMemberDialog").assertDoesNotExist()

    }

    @Test
    fun GroupMemberScreen_should_set_page_texes_to_group_data() {

        //Assert: group name and currency should be equal to mockGroup's name and currency
        composeTestRule.onNodeWithTag("groupName").assertIsDisplayed()
            .assertTextEquals(mockGroup.name)
        composeTestRule.onNodeWithTag("groupCurrency").assertIsDisplayed()
            .assertTextEquals("واحد پولی: " + mockGroup.currency)
        composeTestRule.onNodeWithTag("groupMembersCount").assertIsDisplayed()
            .assertTextEquals(mockGroup.members!!.size.toString() + " عضو")

        composeTestRule.onAllNodesWithTag("userItem").assertCountEquals(mockGroup.members!!.size)

    }


}