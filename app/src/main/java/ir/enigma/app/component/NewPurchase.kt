package ir.enigma.app.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.enigma.app.model.User

@Composable
fun NewPurchase(user: User) {
    val displayNumber = remember { mutableStateOf("0.0") }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        AvatarWithText(user)
        PlusMinusButtons(displayNumber)
    }

}

@Preview(showBackground = true)
@Composable
fun previ() {
    RtlThemePreview {
//        NewPurchase(userE)
    }
}

