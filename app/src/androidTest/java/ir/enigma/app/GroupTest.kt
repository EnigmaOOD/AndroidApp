package ir.enigma.app

import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onSibling
import io.mockk.coEvery
import io.mockk.mockk
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.navigation.Screen
import kotlinx.coroutines.flow.flowOf
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
    fun should_purchase_lazy_column_children_count_is_2_when_api_result_has_tow_groups() {

        //Assert:
        composeTestRule.onNodeWithTag("purchaseLazyColumn").onChildren()[0].assertExists()
        composeTestRule.onNodeWithTag("purchaseLazyColumn").onChildren()[1].assertExists()
    }
}