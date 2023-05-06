package ir.enigma.app.ui.main

import InputTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.ui.theme.SpaceThin

@Composable
fun AddGroupScreen(navController: NavController) {
    val grpName = remember { mutableStateOf("") }
    val currency = remember { mutableStateOf("") }
    val newEmail = remember { mutableStateOf("") }
    var selectedIndex = remember { mutableStateOf(0) }

    val grpCategoriesName = listOf("سفر", "خانه", "مهمانی", "سایر")
    val grpCategoriesIcon = listOf(
        R.drawable.ic_airplane, R.drawable.ic_home, R.drawable.ic_people, R.drawable.ic_fill_gamepad
    )

    val scrollState = rememberScrollState()

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
        Column() {
            Column(modifier = Modifier.verticalScroll(scrollState).weight(1f)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    elevation = 0.dp
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        TextH6(text = "اطلاعات گروه")
                        TVSpacer()
                        InputTextField(
                            text = grpName,
                            label = "نام گروه",
                        )
                        InputTextField(
                            text = currency,
                            label = "واحد پول",
                        )

                        MVSpacer()

                        TextSubtitle1(text = "دسته بندی")
                        TVSpacer()
                        MultiToggleButton(
                            options = grpCategoriesName,
                            drawableId = grpCategoriesIcon,
                            selectedIndex = selectedIndex.value,
                            onSelectedIndexChange = { index -> selectedIndex.value = index }
                        )
                    }
                }

                LVSpacer()

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    elevation = 0.dp
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        TextH6(text = "اعضا")
                        TVSpacer()
                        InputTextField(
                            text = newEmail,
                            label = "ایمیل عضو جدید",
                            onValueChange = { newEmail.value = it }
                        )
                        AddOutlinedButton()
                    }
                }
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
}

@Preview
@Composable
fun v() {
    RtlThemePreview {
        AddGroupScreen(rememberNavController())
    }
}