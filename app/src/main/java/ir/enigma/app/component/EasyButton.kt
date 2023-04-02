package com.stylist.app.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp



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
            SecoundaryButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.SecondaryText ->
            SecoundaryTextButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
        ButtonTheme.SecondaryOutline ->
            SecoundaryOutLineButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enable,
                content = content
            )
    }
}

@Composable
fun LoadingButton(
    modifier: Modifier,
    theme: ButtonTheme = ButtonTheme.Primary,
    actionText: String,
    actionContent: @Composable () -> Unit = { Text(actionText) },
    loadingContent: @Composable () -> Unit = {
        Text("")
        val size = 25.dp
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = MaterialTheme.colors.onPrimary
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