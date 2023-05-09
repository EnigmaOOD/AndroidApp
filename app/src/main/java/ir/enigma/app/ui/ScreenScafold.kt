package ir.enigma.app.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.enigma.app.component.TextBody2
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.theme.*
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T : ApiResult<*>> ApiScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    apiResult: MutableState<T>,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    loading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {

    val result = apiResult.value
    val message: Message? = getMessageByResult(result)


    Scaffold(
        modifier,
        scaffoldState,
        topBar,
        bottomBar,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.fillMaxHeight(),
                hostState = it
            ) { snackbarData: SnackbarData ->
                Box() {
                    Snackbar(
                        snackbarData = snackbarData,
                        Modifier.align(Alignment.TopCenter),
                        elevation = 0.dp,
                        backgroundColor = getMessageBackgroundColor(
                            message?.type ?: MessageType.INFO
                        ),
                    )
                }
            }
        },
        floatingActionButton,
        floatingActionButtonPosition,
        isFloatingActionButtonDocked,
        drawerContent,
        drawerGesturesEnabled,
        drawerShape,
        drawerElevation,
        drawerBackgroundColor,
        drawerContentColor,
        drawerScrimColor,
        backgroundColor,
        contentColor,
        content
    )

    if (message != null) {
        LaunchedEffect(Unit) {
            scaffoldState.snackbarHostState.showSnackbar(
                message.text,
                duration = snackbarDuration
            )
            apiResult.value = ApiResult.Empty() as T
        }
    }


    if (loading)
        Loading()


}

@Composable
fun Loading() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun getMessageBackgroundColor(messageType: MessageType): Color {
    return when (messageType) {
        MessageType.SUCCESS -> MaterialTheme.colors.secondary
        MessageType.ERROR -> MaterialTheme.colors.error
        MessageType.WARNING -> MaterialTheme.colors.onBackground
        MessageType.INFO -> MaterialTheme.colors.onBackground
    }

}


fun getMessageByResult(result: ApiResult<*>?): Message? {
    return if (result != null) {
        when (result) {
            is ApiResult.Success -> {
                if (result.message.isNullOrEmpty())
                    return null
                Message(
                    type = MessageType.SUCCESS,
                    text = result.message!!
                )
            }
            is ApiResult.Error -> {
                Message(
                    type = MessageType.ERROR,
                    text = result.message!!
                )
            }
            else -> null
        }
    } else {
        null
    }
}

data class Message(val text: String, val type: MessageType)

enum class MessageType {
    SUCCESS, ERROR, WARNING, INFO
}
