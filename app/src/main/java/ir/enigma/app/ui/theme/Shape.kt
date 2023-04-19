package ir.enigma.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(CornerButton),
    medium = RoundedCornerShape(CornerButton),
    large = RoundedCornerShape(0.dp)
)

@get:Composable
val Shapes.toast: Shape
    get() = RoundedCornerShape(5.dp)

