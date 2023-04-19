package ir.enigma.app.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.*


@Composable
fun BackIconButton(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
    size: Dp = IconSmall,
    rotation: Float = if (LocalLayoutDirection.current == LayoutDirection.Rtl) 180f else 0f,

    onClick: () -> Unit,
) {
    IconButton(

        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, //TODO back icon drawable
            contentDescription = "back",
            tint = tint,
            modifier = Modifier.rotate(rotation).size(size)
        )
    }
}


@Composable
fun SquircleIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    backgroundColor: Color,
    iconTint: Color,
    size: Dp = 60.dp,
) {
    Box(modifier = modifier.size(size)) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_squircle),
            tint = backgroundColor,
            contentDescription = "squircle",
        )
        Icon(
            modifier = Modifier.fillMaxSize().padding(10.dp).align(Alignment.Center),
            painter = painterResource(iconId),
            contentDescription = "icon",
            tint = iconTint,
        )
    }

}

@Composable
fun EasyIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconId: Int,
    size: Dp = IconMedium,
    tint: Color = MaterialTheme.colors.onBackgroundAlpha7,
    padding: Dp = IconDefaultPadding
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(iconId),
            contentDescription = null,
            tint = tint
        )
    }
}
