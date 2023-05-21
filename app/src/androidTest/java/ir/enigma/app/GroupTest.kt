package ir.enigma.app

import androidx.compose.ui.test.*
import io.mockk.coEvery
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.util.toPrice
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GroupTest : BaseUiTest(Screen.GroupScreen.name) {

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        initMeAndToken()
        coEvery { mainRepository.getGroupPurchases(any(), any(), any()) } returns ApiResult.Success(
            flowOf(listOf(mockPurchase1, mockPurchase2))
        )
        coEvery { mainRepository.getGroupWithMembers(any(), any()) } returns ApiResult.Success(
            mockGroup
        )
        setComposeTestRule()
    }

    @Test
    fun GroupScreen_should_purchase_lazy_column_children_count_is_2_when_api_result_has_tow_groups() {

        //Assert:
        composeTestRule.onAllNodesWithTag("purchaseCard").assertCountEquals(2)


    }


    @Test
    fun GroupScreen_should_display_purchase_dialog_when_a_purchase_clicked() {

        //Act: click on purchase item
        composeTestRule.onAllNodesWithTag("purchaseCard")[0].performClick()

        //Assert: purchase dialog should be displayed and total price should be equal to purchase[0]'s total price
        composeTestRule.onNodeWithTag("purchaseTotalPrice")
            .assertIsDisplayed()
            .assertTextEquals("قیمت کل:  " + mockPurchase1.totalPrice.toPrice(mockGroup.currency))


    }

    @Test
    fun GroupScreen_should_navigate_to_members_screen_with_shoing_all_members_when_click_on_top_bar() {

        //Act: click on top bar
        composeTestRule.onNodeWithTag("topAppBar").performClick()

        //Assert: members screen should be displayed
        composeTestRule.onNodeWithTag("membersLazyColumn").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("userItem").assertCountEquals(2)
    }

    @Test
    fun GroupScreen_should_navigate_to_add_purchase_screen_when_click_on_plus_button() {

        //Act: click on addPurchase button
        composeTestRule.onNodeWithTag("addPurchase").performClick()

        //Assert: add purchase screen should be displayed
        composeTestRule.onNodeWithTag("NewPurchaseScreen").assertIsDisplayed()
        //assert page in new purchase state
        composeTestRule.onNodeWithTag("priceTextField").onChildren()[0].assertIsEnabled()
            .assertTextEquals("قیمت", "0")

    }

    @Test
    fun GroupScreen_should_navigate_to_settle_up_screen_when_click_on_suddle_up_button() {

        //Act: click on addPurchase button
        composeTestRule.onNodeWithTag("leaveOrSettleUp").performClick()

        //Assert: add purchase screen should be displayed
        composeTestRule.onNodeWithTag("NewPurchaseScreen").assertIsDisplayed()
        //assert page in settle up state
        composeTestRule.onNodeWithTag("priceTextField").onChildren()[0].assertIsNotEnabled()
            .assertTextEquals("قیمت", "2000.0")
    }


}