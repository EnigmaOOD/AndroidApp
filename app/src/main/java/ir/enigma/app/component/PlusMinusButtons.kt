package ir.enigma.app.component

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.R
import ir.enigma.app.ui.theme.BorderThin
import ir.enigma.app.ui.theme.IconSmall
import ir.enigma.app.ui.theme.onBackgroundAlpha3
import ir.enigma.app.util.zeroIfEmpty

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlusMinusButtons(displayNumber: MutableState<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        EasyIconButton(
            modifier = Modifier.testTag("plus"),
            onClick = { displayNumber.value = plus(displayNumber.value.toDouble()).toString() },
            iconId = R.drawable.ic_add_circle,
            tint = MaterialTheme.colors.onBackground,
            size = IconSmall
        )

        BasicTextField(
            modifier = Modifier.testTag("related")
                .border(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colors.onBackgroundAlpha3,
                    width = BorderThin
                )
                .width(45.dp),
            value = displayNumber.value,
            onValueChange = { displayNumber.value = it.zeroIfEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = displayNumber.value,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember { MutableInteractionSource() },
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 4.dp,
                    end = 4.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }

        EasyIconButton(
            onClick = {
                try {
                    displayNumber.value = minus(displayNumber.value.toDouble()).toString()

                }
                catch (e:java.lang.NumberFormatException){

                }
            },
            modifier = Modifier.testTag("minus"),
            iconId = R.drawable.ic_minus_cirlce,
            tint = MaterialTheme.colors.onBackground,
            size = IconSmall,
            enabled = displayNumber.value.toDouble() > 0,
        )
    }

}

fun plus(number: Double): Double {
    var decimal = number - number.toInt()
    if (decimal < 0.5)
        return number.toInt() + 0.5
    else
        return number.toInt() + 1.0
}

fun minus(number: Double): Double {
    val decimal = number - number.toInt()
    if (decimal == 0.0)
        return number.toInt() - 0.5
    else if (decimal <= 0.5)
        return number.toInt().toDouble()
    else
        return number.toInt() + 0.5
}


@Preview(showBackground = true)
@Composable
fun re() {
    val displayNumber = remember { mutableStateOf("0.0") }
    RtlThemePreview {
        PlusMinusButtons(displayNumber)
    }
}