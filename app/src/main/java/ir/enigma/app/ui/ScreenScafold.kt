package ir.enigma.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.enigma.app.component.TextBody2
import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.theme.*
import kotlinx.coroutines.delay


@Composable
fun <T : ApiResult<*>> ApiScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
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
    messageDuration: Long = 5000,
    loading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {


    Box {
        Scaffold(
            modifier,
            scaffoldState,
            topBar,
            bottomBar,
            snackbarHost,
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
        val result = apiResult.value
        val message: Message? = getMessageByResult(result)


        if (message != null) {
            ShowMessageCard(message = message)

            LaunchedEffect(Unit) {
                delay(messageDuration)
                apiResult.value = ApiResult.Empty() as T
            }
        }

        if (loading)
            Loading()


    }
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
fun ShowMessageCard(message: Message) {

    Card(
        shape = MaterialTheme.shapes.toast,
        backgroundColor =
        when (message.type) {
            MessageType.SUCCESS -> MaterialTheme.colors.secondary
            MessageType.ERROR -> MaterialTheme.colors.error
            MessageType.WARNING -> MaterialTheme.colors.primary
            MessageType.INFO -> MaterialTheme.colors.primary
        },
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = SpaceMedium, vertical = SpaceThin)
    ) {
        TextBody2(
            modifier = Modifier.padding(vertical = SpaceSmall, horizontal = SpaceMedium),
            text = message.text
        )
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
