package ir.enigma.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
fun EnigmaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isPreview: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette


    if (!isPreview) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = colors.background
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}