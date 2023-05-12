package ir.enigma.app.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.model.User
import ir.enigma.app.ui.theme.BorderThin
import ir.enigma.app.ui.theme.onBackgroundAlpha3
import ir.enigma.app.util.zeroIfEmpty

@Composable
fun MemberContributionItem(
    memberContribution: MemberContribution,
    isRelated: Boolean,
    enabled: Boolean = true
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarWithText(memberContribution.user)
        if (enabled) {
            if (isRelated) {
                PlusMinusButtons(memberContribution.related)
            } else {

                BasicTextField(
                    modifier = Modifier.widthIn(50.dp, 100.dp).border(
                        shape = RoundedCornerShape(3.dp),
                        color = MaterialTheme.colors.onBackgroundAlpha3,
                        width = BorderThin
                    ),
                    value = memberContribution.exact.value,
                    onValueChange = { memberContribution.exact.value = it.zeroIfEmpty() },
                    textStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )
            }
        } else
            Box { }
    }

}

class MemberContribution(
    val user: User,
    val related: MutableState<String>,
    val exact: MutableState<String>,
)

@Preview(showBackground = true)
@Composable
fun previ() {
    RtlThemePreview {
//        NewPurchase(userE)
    }
}

