package ir.enigma.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = blue,
    primaryVariant = blue,
    secondary = green,
    error = red,
    background = Color.White,
    surface = light_gray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onError = Color.White,
    onBackground = dark_gray,
    onSurface = dark_gray,
)

@Composable
fun EnigmaAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = LightColorPalette


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}