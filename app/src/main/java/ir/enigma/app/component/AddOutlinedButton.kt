package ir.enigma.app.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.enigma.app.ui.theme.BorderThin
import ir.enigma.app.ui.theme.onBackgroundAlpha3

@Composable
fun AddOutlinedButton(modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = { /*TODO*/ },
        border = BorderStroke(
            color = MaterialTheme.colors.onBackgroundAlpha3,
            width = BorderThin
        ),
    ) {
        TextBody2(text = "افزودن")
    }
}

@Preview()
@Composable
fun p() {
    RtlThemePreview {
        AddOutlinedButton()
    }
}