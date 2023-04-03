package ir.enigma.app.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.enigma.app.ui.theme.EnigmaAppTheme
import ir.enigma.app.R
import ir.enigma.app.ui.theme.IconDefaultPadding
import ir.enigma.app.ui.theme.IconMedium


@Composable
fun CardWithImageOrIcon(
    modifier: Modifier = Modifier,
    icon: Boolean,
    resource: Int,
    size: Dp = IconMedium,
    contentPadding: Dp = IconDefaultPadding,
    tint: Color = MaterialTheme.colors.onBackground,
    elevation: Dp = 0.dp,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentDescription: String? = null,
) {
    Card(
        modifier = modifier,
        elevation = elevation, shape = CircleShape, backgroundColor = backgroundColor
    ) {
        if (icon) {
            Icon(
                modifier = Modifier
                    .size(size)
                    .padding(contentPadding),
                painter = painterResource(id = resource),
                contentDescription = contentDescription,
                tint = tint
            )
        } else {
            Image(
                modifier = Modifier
                    .size(size)
                    .padding(contentPadding),
                painter = painterResource(id = resource),
                contentDescription = contentDescription
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun pre() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        EnigmaAppTheme {
            CardWithImageOrIcon(
                icon = false,
                resource = R.drawable.ic_fill_hotel,
                size = 30.dp,
                contentPadding = 10.dp
            )
        }
    }
}