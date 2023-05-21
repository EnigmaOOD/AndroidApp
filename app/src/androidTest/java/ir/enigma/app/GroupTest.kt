package ir.enigma.app

import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onSibling
import io.mockk.mockk
import ir.enigma.app.component.RtlThemePreview
import ir.enigma.app.ui.navigation.Screen
import org.junit.Before

class GroupTest : BaseUiTest(Screen.GroupScreen.name) {

    @Before
    override fun setUp() {
        // Arrange of all tests in this class
        super.setUp()

    }
}