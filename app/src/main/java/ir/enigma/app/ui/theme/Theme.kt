package ir.enigma.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


/** use for
 * hint texts
 * disabled icon tints
 */
@get:Composable
val Colors.onBackgroundAlpha7: Color
    get() = onBackground.copy(alpha = .7f)

/** use for
 * disabled texts
 */
@get:Composable
val Colors.onBackgroundAlpha3: Color
    get() = onBackground.copy(alpha = .3f)

/** use for
 * outline buttons border
 * dividers
 */
@get:Composable
val Colors.onBackgroundAlpha1: Color
    get() = onBackground.copy(alpha = .1f)

@Composable
fun EnigmaAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}