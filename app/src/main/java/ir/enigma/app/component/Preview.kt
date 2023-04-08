package ir.enigma.app.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.enigma.app.ui.theme.EnigmaAppTheme

@Composable
fun RtlThemePreview(preview: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        EnigmaAppTheme(isPreview = true) {
            preview()
        }
    }
}