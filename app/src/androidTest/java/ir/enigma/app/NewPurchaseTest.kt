package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class NewPurchaseTest : BaseUiTest(Screen.GroupScreen.name) {


    lateinit var categoryTextField: SemanticsNodeInteraction
    lateinit var priceTextField: SemanticsNodeInteraction
    lateinit var changeBuyersButton: SemanticsNodeInteraction
    lateinit var changeConsumersButton: SemanticsNodeInteraction
    lateinit var buyersError: SemanticsNodeInteraction
    lateinit var consumersError: SemanticsNodeInteraction
    lateinit var confirmButton: SemanticsNodeInteraction
    lateinit var categorySelectScreen: SemanticsNodeInteraction
    lateinit var categoryItem: SemanticsNodeInteractionCollection
    lateinit var selectMembersDialog: SemanticsNodeInteraction
    lateinit var selectAllButton: SemanticsNodeInteraction
    lateinit var deleteAllButton: SemanticsNodeInteraction
    lateinit var plusButtonNodes: SemanticsNodeInteractionCollection
    lateinit var minusButtonNodes: SemanticsNodeInteractionCollection
    lateinit var relatedTfNodes: SemanticsNodeInteractionCollection
    lateinit var exactTfNodes: SemanticsNodeInteractionCollection
    lateinit var relatedSegNodes: SemanticsNodeInteractionCollection
    lateinit var exactSegNodes: SemanticsNodeInteractionCollection


    @Before
    override fun setUp() {
        //Arrange
        super.setUp()
        initMeAndToken()


        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        coEvery { mainRepository.getGroupPurchases(any(), any(), any()) } returns ApiResult.Success(
            flowOf(emptyList())
        )
        setComposeTestRule()
        //navigate to new purchase screen
        composeTestRule.onNodeWithTag("addPurchase").performClick()
        categoryTextField = composeTestRule.onNodeWithTag("categoryTextField")
        priceTextField = composeTestRule.onNodeWithTag("priceTextField").onChildren()[0]
        changeBuyersButton = composeTestRule.onNodeWithTag("changeBuyersButton")
        changeConsumersButton = composeTestRule.onNodeWithTag("changeConsumersButton")
        buyersError = composeTestRule.onNodeWithTag("buyersError")
        consumersError = composeTestRule.onNodeWithTag("consumersError")
        confirmButton = composeTestRule.onNodeWithTag("createPurchaseButton")
        categorySelectScreen = composeTestRule.onNodeWithTag("categorySelectScreen")
        categoryItem = composeTestRule.onAllNodesWithTag("categoryItem")
        selectMembersDialog = composeTestRule.onNodeWithTag("selectMembersDialog")
        selectAllButton = composeTestRule.onNodeWithTag("selectAllButton")
        deleteAllButton = composeTestRule.onNodeWithTag("deleteAllButton")
        relatedTfNodes = composeTestRule.onAllNodesWithTag("related")
        exactTfNodes = composeTestRule.onAllNodesWithTag("exact")
        plusButtonNodes = composeTestRule.onAllNodesWithTag("plus")
        minusButtonNodes = composeTestRule.onAllNodesWithTag("minus")
        relatedSegNodes = composeTestRule.onAllNodesWithTag("SegmentedControlButton0")
        exactSegNodes = composeTestRule.onAllNodesWithTag("SegmentedControlButton1")

    }


    @Test
    fun NewPurchaseScreen_should_show_errors_when_fields_is_empty_and_click_confirm() {

        //Act: click on create purchase button to show errors
        confirmButton.performClick()

        //Assert: errors should be displayed
        priceTextField.onSibling().assertIsDisplayed()
            .assertTextEquals("قیمت نمی\u200Cتواند 0 باشد")
        buyersError.assertIsDisplayed().assertTextEquals("لیست خریدارها نمی\u200Cتواند خالی باشد")
    }

    @Test
    fun NewPurchaseScreen_should_plus_tf_when_click_plus_button() {
        //Act: click plus button
        plusButtonNodes[0].performClick()

        //Assert: tf should be 1.5
        relatedTfNodes[0].assertTextEquals("1.5")
    }

    @Test
    fun NewPurchaseScreen_should_minus_tf_when_click_plus_button() {
        //Act: click plus button
        minusButtonNodes[0].performClick()

        //Assert: tf should be 0.5
        relatedTfNodes[0].assertTextEquals("0.5")
    }

    @Test
    fun NewPurchaseScreen_should_toggle_exact_when_click_on_exact_button() {
        //Act: click exact button
        exactSegNodes[1].performClick()

        //Assert: all exact text fields should displayed
        exactTfNodes[0].assertIsDisplayed()

    }

    @Test
    fun NewPurchaseScreen_should_display_members_dialog_when_change_buyers_button_clicked() {
        //Act: click exact button
        changeBuyersButton.performClick()

        //Assert: show membersDialog
        selectMembersDialog.assertIsDisplayed()

    }

    @Test
    fun SelectMembersDialog_should_add_buyers_in_buyres_column_when_a_member_item_clicked() {
        //Arrange: show members dialog
        changeBuyersButton.performClick()

        //Act: click on a member item
        composeTestRule.onAllNodesWithTag("memberItem")[0].performClick()

        //Assert: buyer column should be displayed
        composeTestRule.onAllNodesWithTag("memberName")[0].assertIsDisplayed().assertTextEquals(
            mockGroup.members!![0].user.name
        )
    }

    @Test
    fun SelectMembersDialog_should_add_consumers_in_consumers_column_when_a_member_item_clicked() {
        //Arrange: show members dialog and clear list
        changeConsumersButton.performClick()
        deleteAllButton.performClick()

        //Act: click on a member item
        composeTestRule.onAllNodesWithTag("memberItem")[0].performClick()

        //Assert: buyer column should be displayed
        composeTestRule.onAllNodesWithTag("memberName")[0].assertIsDisplayed().assertTextEquals(
            mockGroup.members!![0].user.name
        )

    }

    @Test
    fun SelectMembersDialog_should_select_all_members_when_click_on_select_all_button() {
        //Arrange: show members dialog and delete all selected
        changeBuyersButton.performClick()

        //Act: click on select all button
        selectAllButton.performClick()

        //Assert: all members should be selected, consumers all selected by default so we should have 2 * members
        composeTestRule.onAllNodesWithTag("memberName")
            .assertCountEquals(mockGroup.members!!.size * 2)
    }


    @Test
    fun NewPurchaseScreen_should_show_category_select_screen_when_click_on_category_text_field() {
        //Act: click on category text field
        categoryTextField.performClick()

        //Assert: category select screen should be displayed
        categorySelectScreen.assertIsDisplayed()
    }

    @Test
    fun NewPurchaseScreen_should_show_category_select_screen_when_click_on_category_item() {
        //Act: click on category text field
        categoryTextField.performClick()
        categoryItem[0].performClick()

        //Assert: category select screen should be displayed
        categoryTextField.assertTextEquals("بابت", "فست\u200Cفود")
    }


    @Test
    fun NewPurchaseScreen_should_show_error_when_total_price_not_equals_to_sum_of_consumers_price_and_conform_clicked() {
        //Arrange: toggle exact button
        exactSegNodes[1].performClick()

        //Act: change exact text fields
        priceTextField.performTextInput("20")
        exactTfNodes[0].performTextInput("1000")
        exactTfNodes[1].performTextInput("1000")

        //Assert: error should be displayed(20 != 2000)
        confirmButton.performClick()
        consumersError.assertIsDisplayed()
            .assertTextEquals("مجموع مبلغ ها با قیمت کل خرید برابر نیست")
    }

    @Test
    fun NewPurchaseScreen_should_show_error_when_all_weight_consumers_is_zero_and_conform_clicked() {

        //Arrange: set price text-field and click minus buttons to set all of related nodes to zero
        // most click each 2 times to set zero
        priceTextField.performTextInput("20")
        minusButtonNodes[0].performClick()
        minusButtonNodes[0].performClick()
        minusButtonNodes[1].performClick()
        minusButtonNodes[1].performClick()

        //Assert: error should be displayed(20 != 2000)
        confirmButton.performClick()
        consumersError.assertIsDisplayed()
            .assertTextEquals("حداقل وزن یک نفر باید بیشتر از صفر باشد")

    }

    @Test
    fun NewPurchaseScreen_should_navigate_to_group_screen_when_no_errors_happens() {
        //Arrange: mock createPurchase set price text-field , set a buyer
        coEvery { mainRepository.createPurchase(any(), any(), any()) } returns ApiResult.Success(
            Unit
        )
        priceTextField.performTextInput("20")
        changeBuyersButton.performClick()
        composeTestRule.onAllNodesWithTag("memberItem")[0].performClick()

        //Act: click on confirm button
        confirmButton.performClick()

        //Assert: group screen should be displayed
        composeTestRule.onNodeWithTag("groupScreen").assertIsDisplayed()
    }

}