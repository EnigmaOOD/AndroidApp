package ir.enigma.app.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ir.enigma.app.ui.theme.BorderMedium
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.ui.theme.onBackgroundAlpha1

@Composable
fun HorizontalDivider(
    width: Dp = BorderMedium,
    verticalPadding: Dp = SpaceThin,
    color: Color = MaterialTheme.colors.onBackgroundAlpha1
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = verticalPadding)
            .width(width)
            .background(color = color)
    )
}