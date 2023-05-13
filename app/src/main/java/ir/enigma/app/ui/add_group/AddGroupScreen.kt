package ir.enigma.app.ui.main

import InputTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.theme.SpaceThin

@Composable
fun AddGroupScreen(navController: NavController, addGroupViewModel: AddGroupViewModel) {
    val grpName = remember { mutableStateOf("") }
    val currency = remember { mutableStateOf("") }
    var selectedIndex = remember { mutableStateOf(0) }

    val grpCategoriesName = listOf("سفر", "خانه", "مهمانی", "سایر")
    val grpCategoriesIcon = listOf(
        R.drawable.ic_airplane,
        R.drawable.ic_home,
        R.drawable.ic_people,
        R.drawable.ic_fill_gamepad
    )

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary
    )

    val members = remember {
        mutableStateListOf<MutableState<String>>()
    }
    val state = addGroupViewModel.state.value

    if (state is ApiResult.Loading)  // = if (addGroupViewModel.state.value.status == ApiStatus.LOADING)
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }

    if (state is ApiResult.Success)
        LaunchedEffect(key1 = Unit) {
            navController.popBackStack()
        }


    ApiScreen(
        modifier = Modifier.fillMaxSize(),
        apiResult = addGroupViewModel.state,
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                TextH6(text = "گروه جدید" , color = MaterialTheme.colors.onPrimary)
            }
        },

        ) { it ->
        val topPaddingValues = it.calculateTopPadding()
        Column() {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
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

                        TextH6(text = "دسته بندی")
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

                        for (member in members) {
                            InputTextField(
                                text = member,
                                label = "ایمیل عضو جدید",
                                keyboardType = KeyboardType.Email,
                                onValueChange = { member.value = it }
                            )
                        }

                        AddOutlinedButton{
                            members.add(mutableStateOf(""))
                        }
                    }
                }
            }
            EasyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                onClick = {
                    val newGroup = AddGroupRequest(
                        name = grpName.value,
                        currency = currency.value,
                        emails = members.map { mutableState -> mutableState.value },
                        picture_id = selectedIndex.value
                    )
                    addGroupViewModel.createGroup(newGroup)

                },
                text = "تایید"
            )
        }
    }

}

