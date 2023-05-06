package ir.enigma.app.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.IconSmall

@Composable
fun MultiToggleButton(
    options: List<String>,
    drawableId: List<Int>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit
) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        options.forEachIndexed { index, text ->

            val painter: Painter = painterResource(id = drawableId[index])

            Button(
                onClick = { onSelectedIndexChange(index) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (index == selectedIndex) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.background
                    }
                )
            ) {
                Icon(
                    painter = painter,
                    modifier = Modifier
                        .size(IconSmall)
                        .align(alignment = Alignment.CenterVertically),
                    contentDescription = null,
                )
                THSpacer()
                Text(text = text)
            }
            MHSpacer()
        }
    }
}