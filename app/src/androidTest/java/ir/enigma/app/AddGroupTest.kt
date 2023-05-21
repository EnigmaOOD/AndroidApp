package ir.enigma.app

import androidx.compose.ui.test.*
import ir.enigma.app.ui.navigation.Screen
import org.junit.Before
import org.junit.Test

class AddGroupTest: BaseUiTest(Screen.AddGroupScreen.name) {
    // define all local Semantic nodes in testRegisterAndLogin here
    lateinit var tfName: SemanticsNodeInteraction
    lateinit var tfNameError: SemanticsNodeInteraction
    lateinit var btnEdit: SemanticsNodeInteraction
    lateinit var btnExit: SemanticsNodeInteraction
    lateinit var snackbar: SemanticsNodeInteraction

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()
        setComposeTestRule()
        tfName = composeTestRule.onNodeWithTag("nameTextField").onChildren()[0]
        tfNameError = tfName.onSibling()
        btnEdit = composeTestRule.onNodeWithTag("editButton")
        btnExit = composeTestRule.onNodeWithTag("exitButton")
        snackbar = composeTestRule.onNodeWithTag("snackbar")
    }

    @Test
    fun register_should_display_errors_when_empty_fields() {

        //Act: click on submit button when text fields are empty

        //Assert: errors should be displayed

    }
}