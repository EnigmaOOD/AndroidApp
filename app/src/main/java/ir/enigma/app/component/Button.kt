package ir.enigma.app.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.enigma.app.ui.theme.BorderMedium
import ir.enigma.app.ui.theme.BorderThin

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

@Composable
fun SecondaryOutLineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.secondary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

@Composable
fun SecondaryTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    TextButton(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}

//**
// outlineButton that using background color instead surface color for background
@Composable
fun BackgroundOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = ButtonDefaults.outlinedBorder,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = MaterialTheme.colors.background
    ),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick,
        modifier,
        enabled,
        interactionSource,
        elevation,
        shape,
        border,
        colors,
        contentPadding,
        content
    )
}


@Composable
fun EasyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTheme: ButtonTheme = ButtonTheme.Primary,
    text: String = "",
    enable: Boolean = true,
    content: @Composable RowScope.() -> Unit = {
        Text(text = text)
    }
) {

    when (buttonTheme) {
        ButtonTheme.Primary ->
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.PrimaryText -> TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enable,
            content = content
        )
        ButtonTheme.PrimaryOutline ->
            OutlinedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.Secondary ->
            SecondaryButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.SecondaryText ->
            SecondaryTextButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.SecondaryOutline ->
            SecondaryOutLineButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
    }
}

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    theme: ButtonTheme = ButtonTheme.Primary,
    actionText: String,
    actionContent: @Composable () -> Unit = { Text(actionText) },
    loadingContent: @Composable () -> Unit = {
        Text("")
        val size = 25.dp
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = MaterialTheme.colors.onPrimary,
            strokeWidth = BorderMedium
        )
    },
    loading: Boolean,
    clickEnableWhenLoading: Boolean = false,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    EasyButton(modifier = modifier, onClick = {
        if (clickEnableWhenLoading || !loading)
            onClick()
    }, enable = enable, buttonTheme = theme, content = {
        if (loading)
            loadingContent()
        else
            actionContent()
    })
}

enum class ButtonTheme {
    Primary, PrimaryText, PrimaryOutline,
    Secondary, SecondaryText, SecondaryOutline
}