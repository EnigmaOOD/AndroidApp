//package ir.enigma.app.component
//
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import android.content.Context
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.ui.platform.*
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.lifecycle.*
//import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
//
//
//import ir.enigma.app.ui.theme.SpaceThin
//import ir.enigma.app.ui.theme.Vazirmatn
//
//object SweetToastUtil {
//
//    @Composable
//    fun MessageToast(
//        message: Message,
//        duration: Int = Toast.LENGTH_LONG,
//        padding: PaddingValues = PaddingValues(vertical = SpaceThin),
//        contentAlignment: Alignment = Alignment.TopStart,
//        isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
//    ) {
//
//        CustomToast(
//            message = message.text,
//            duration = duration,
//            padding = padding,
//            contentAlignment = contentAlignment,
//            type = messageTypeToToastType(message.type),
//            isRtl = isRtl
//        )
//    }
//
//    var sweetToast: SweetToast? = null
//
//    @Composable
//    fun CustomToast(
//        message: String,
//        duration: Int = Toast.LENGTH_LONG,
//        padding: PaddingValues,
//        contentAlignment: Alignment,
//        type: ToastType,
//        isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl
//    ) {
//
//        if (sweetToast?.view?.isShown == true) {
//            sweetToast?.cancel()
//        }
//
//        sweetToast = SweetToast(LocalContext.current)
//        sweetToast!!.MakeTest(
//            message = "ریدم",
//            duration = duration,
//            type = type,
//            padding = padding,
//            contentAlignment = contentAlignment,
//            isRtl = isRtl
//        )
//
//        sweetToast!!.show()
//    }
//
//    @Composable
//    fun SetView(
//        messageTxt: String,
//        backgroundColor: Color,
//        padding: PaddingValues,
//        contentAlignment: Alignment,
//        isRtl: Boolean
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            contentAlignment = contentAlignment
//        ) {
//            Surface(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                color = Color.Transparent
//            ) {
//                Row(
//                    modifier = Modifier
//                        .background(
//                            color = backgroundColor,
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = messageTxt,
//                        style = TextStyle(
//                            textAlign = if (isRtl) TextAlign.End else TextAlign.Start,
//                            fontFamily = Vazirmatn,
//                            color = Color.White,
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.Medium
//                        ),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 10.dp, vertical = 10.dp)
//                    )
//                }
//            }
//        }
//    }
//
//    private fun messageTypeToToastType(type: MessageType): ToastType {
//        return when (type) {
//            MessageType.Success -> ToastType.Success
//            MessageType.Error -> ToastType.Error
//            MessageType.Warning -> ToastType.Warning
//            MessageType.Info -> ToastType.Info
//        }
//    }
//}
//
//
//class SweetToast(context: Context) : Toast(context) {
//
//    @Composable
//    fun MakeTest(
//        message: String,
//        duration: Int,
//        type: ToastType,
//        padding: PaddingValues,
//        contentAlignment: Alignment,
//        isRtl: Boolean
//    ) {
//        val context = LocalContext.current
//
//        val views = ComposeView(context)
//
//        val backgroundColor = when (type) {
//            ToastType.Error -> MaterialTheme.colors.error
//            ToastType.Success -> MaterialTheme.colors.secondary
//            ToastType.Warning -> MaterialTheme.colors.primary
//            ToastType.Info -> MaterialTheme.colors.onBackground
//        }
//
//        views.setContent {
//            SweetToastUtil.SetView(
//                messageTxt = message,
//                backgroundColor = backgroundColor,
//                padding = padding,
//                contentAlignment = contentAlignment,
//                isRtl
//            )
//        }
//
//        ViewTreeLifecycleOwner.set(views, LocalLifecycleOwner.current)
//        ViewTreeViewModelStoreOwner.set(views, LocalViewModelStoreOwner.current)
//    }
//}
//
//enum class ToastType {
//    Error, Success, Warning, Info
//}
