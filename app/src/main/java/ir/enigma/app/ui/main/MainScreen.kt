package ir.enigma.app.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.enigma.app.component.*
import ir.enigma.app.data.*
import ir.enigma.app.ui.group.component.GroupItem
import ir.enigma.app.ui.main.component.MainTopBar
import ir.enigma.app.R
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.main.component.ShimmerColumn
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.background
    )
    val groups = mainViewModel.groupList.collectAsState().value
    LaunchedEffect(Unit) {

        mainViewModel.fetchGroups()
    }
    ApiScreen(
        backgroundColor = MaterialTheme.colors.background,
        apiResult = mainViewModel.state,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddGroupScreen.name)
            }) {

                CardWithImageOrIcon(
                    icon = true,
                    resource = R.drawable.ic_fill_add,
                    size = 50.dp,
                    tint = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.secondary,
                    elevation = 4.dp
                ) {
                    navController.navigate(Screen.AddGroupScreen.name)
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Card(elevation = 0.dp, shape = RectangleShape) {
                MainTopBar(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpaceLarge, vertical = SpaceMedium),
                )
            }
            LVSpacer()
            Card(
                modifier = Modifier
                    .fillMaxWidth().weight(1f)
            ) {
                Column(modifier = Modifier.padding(all = SpaceMedium)) {
                    TextBody2(text = "گروه\u200Cها")
                    if (mainViewModel.state.value.status != ApiStatus.SUCCESS) {
                        MVSpacer()
                        ShimmerColumn(count = 20, circleSize = IconLarge)
                    } else if (groups.isEmpty()) {
                        MVSpacer()
                        HintText(
                            modifier = Modifier.fillMaxWidth(),
                            text = "گروهی عضو نیستید. برای افزودن + را کلیک کنید",
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(top = 15.dp),
                            userScrollEnabled = false
                        ) {
                            itemsIndexed(groups) { index, item ->
                                GroupItem(
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.GroupScreen.name + "/${item.id}")
                                    },
                                    group = item,
                                    amount = mainViewModel.groupToAmount[item.id]?.value,
                                )
                                SVSpacer()
                                if (index != groups.size - 1) {
                                    Divider(startIndent = 55.dp)
                                    SVSpacer()
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
fun pr() {
    RtlThemePreview {
        MainScreen(rememberNavController(), hiltViewModel())
    }

}