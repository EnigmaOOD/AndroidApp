package ir.enigma.app.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardWithImageOrIcon(
    modifier: Modifier = Modifier,
    icon: Boolean,
    resource: Int,
    size: Dp = IconMedium,
    contentPadding: Dp = IconDefaultPadding,
    tint: Color = MaterialTheme.colors.onBackground,
    elevation: Dp = 0.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentDescription: String? = null,
    border: Boolean = false,
    onClick: () -> Unit = {},
) {
    val _modifier =
        if (border)
            modifier
                .border(
                    shape = CircleShape,
                    color = MaterialTheme.colors.primary,
                    width = BorderThin
                )
        else
            modifier

    Card(
        modifier = _modifier,
        elevation = elevation,
        shape = CircleShape,
        backgroundColor = backgroundColor,
        onClick = onClick
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
    RtlThemePreview {
        CardWithImageOrIcon(
            icon = false,
            resource = R.drawable.avt_10,
            size = IconLarge,
            contentPadding = 0.dp,
            border = true
        )
    }
}