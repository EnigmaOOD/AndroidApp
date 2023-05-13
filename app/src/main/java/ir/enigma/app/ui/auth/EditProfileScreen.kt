package ir.enigma.app.ui.auth

import InputTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.component.*
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.data.userAvatars
import ir.enigma.app.ui.theme.IconSemiLarge
import ir.enigma.app.ui.theme.IconVeryLarge
import ir.enigma.app.ui.theme.SpaceSmall
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.R

@Composable
fun EditProfileScreen(navController: NavController) {
    val isShowDialogCharacter = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                TextH6(text = "گروه جدید")
            }
        },

        ) { it ->
        val topPaddingValues = it.calculateTopPadding()

        val name = remember { mutableStateOf(me.name.toString()) }

        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 20.dp)
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardWithImageOrIcon(
                        icon = false,
                        resource = userAvatars[me.iconId],
                        size = IconVeryLarge
                    ) {
                        isShowDialogCharacter.value = !isShowDialogCharacter.value
                    }
                    TVSpacer()
                    HintText(
                        text = "برای تغییر کاراکتر خود کلیک کنید",
                        color = MaterialTheme.colors.onSurface
                    )
                }

                LVSpacer()
                TextBody1(text = "ایمیل شما: ${me.email}")
                LVSpacer()
                InputTextField(
                    text = name,
                    label = "نام و نام خانوادگی",
                    onValueChange = { name.value = it }
                )
            }
            EasyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                onClick = {},
                text = "تایید"
            )
        }
    }

    if (isShowDialogCharacter.value) {
        Dialog(onDismissRequest = {
            isShowDialogCharacter.value = !isShowDialogCharacter.value
        }) {
            Card(elevation = 8.dp) {
                Column(
                    Modifier
                        .background(Color.White)
                        .padding(SpaceSmall)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextSubtitle2(text = "انتخاب کاراکتر")
                        ExitIconButton() {
                            isShowDialogCharacter.value = !isShowDialogCharacter.value
                        }
                    }

                    TVSpacer()

                    LazyColumn() {
                        items(5) { indexC ->
                            LazyRow(
                                modifier = Modifier.fillParentMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                items(4) { indexR ->
                                    val resourceName =
                                        "R.drawable.avt_${indexR + indexC}"
                                    CardWithImageOrIcon(
                                        icon = false,

                                        //ToDo: replace resourceName instead of R.drawable.avt_9
                                        resource = R.drawable.avt_9,

                                        size = IconSemiLarge
                                    ) {
                                        isShowDialogCharacter.value =
                                            !isShowDialogCharacter.value
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun s() {
    RtlThemePreview {
        EditProfileScreen(rememberNavController())
    }
}