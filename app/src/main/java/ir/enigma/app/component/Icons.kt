package com.stylist.app.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.stylist.app.R
import com.stylist.app.ui.theme.IconMedium
import com.stylist.app.ui.theme.IconSmall

@Composable
fun MyIconButton(
    onClick: () -> Unit,
    icon: Int,
) {
    IconButton(
        onClick = { onClick() },
        Modifier.background(
            color = MaterialTheme.colors.surface,
            shape = MaterialTheme.shapes.medium
        )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(IconMedium)
        )
    }

}

@Composable
fun BackIconButton(
    modifier: Modifier,
    tint: Color = MaterialTheme.colors.onBackground,
    size: Dp = IconSmall,
    rotation: Float = if (LocalLayoutDirection.current == LayoutDirection.Rtl) 180f else 0f,
    backgroundColor: Color = MaterialTheme.colors.background,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.background(backgroundColor),
        onClick = onClick
    ) {
        Icon(
            painterResource(R.drawable.ic_arrow_left),
            contentDescription = "back",
            tint = tint,
            modifier = Modifier.rotate(rotation).size(size)
        )
    }
}