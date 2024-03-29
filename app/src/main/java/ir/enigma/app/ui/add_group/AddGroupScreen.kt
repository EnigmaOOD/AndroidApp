package ir.enigma.app.ui.main

import InputTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.GroupCategory
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.add_group.AddGroupViewModel
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer

@Composable
fun AddGroupScreen(navController: NavController, addGroupViewModel: AddGroupViewModel) {
    val grpName = remember { mutableStateOf("") }
    val currency = remember { mutableStateOf("") }
    var selectedIndex = remember { mutableStateOf(0) }

    var showErrors = remember { mutableStateOf(false) }

    val grpCategoriesName = GroupCategory.values().map {
        it.text
    }
    val grpCategoriesIcon = GroupCategory.values().map {
        it.iconRes
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary
    )

    val members = remember {
        mutableStateListOf<MutableState<String>>()
    }
    val state = addGroupViewModel.state.value

    if (state is ApiResult.Loading) {
        MyLog.log(
            StructureLayer.Screen,
            "Composable",
            "AddGroupScreen",
            LogType.Info,
            "show Loading Dialog"
        )
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    if (state is ApiResult.Success) {
        MyLog.log(
            StructureLayer.Screen,
            "Composable",
            "AddGroupScreen",
            LogType.Info,
            "add group success navigate back"
        )
        LaunchedEffect(key1 = Unit) {
            navController.popBackStack()
        }
        addGroupViewModel.empty()
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
                TextH6(text = "گروه جدید", color = MaterialTheme.colors.onPrimary)
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
                            modifier = Modifier.testTag("groupNameTextField"),
                            text = grpName,
                            label = "نام گروه",
                            hasError = grpName.value.isEmpty(),
                            error = "نام گروه نمی\u200Cتواند خالی باشد",
                            showError = showErrors.value,
                            onValueChange = { grpName.value = it }
                        )
                        InputTextField(
                            modifier = Modifier.testTag("groupCurrencyTextField"),
                            text = currency,
                            label = "واحد پول",
                            hasError = currency.value.isEmpty(),
                            error = "واحد پول نمی\u200Cتواند خالی باشد",
                            showError = showErrors.value,
                            onValueChange = { currency.value = it }
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
                                modifier = Modifier.testTag("emailField"),
                                text = member,
                                label = "ایمیل عضو جدید",
                                keyboardType = KeyboardType.Email,
                                onValueChange = { member.value = it }
                            )
                        }

                        AddOutlinedButton(modifier = Modifier.testTag("addEmailField")) {
                            members.add(mutableStateOf(""))
                        }
                    }
                }
            }
            EasyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .testTag("addGroupButton"),
                onClick = {
                    showErrors.value = true
                    val newGroup = AddGroupRequest(
                        name = grpName.value,
                        currency = currency.value,
                        emails = members.map { mutableState -> mutableState.value },
                        picture_id = selectedIndex.value
                    )
                    if (grpName.value.isNotEmpty() && currency.value.isNotEmpty()) {
                        addGroupViewModel.createGroup(newGroup)
                    }

                },
                text = "تایید"
            )
        }
    }

}

