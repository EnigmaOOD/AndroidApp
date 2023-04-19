package ir.enigma.app.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.component.*
import ir.enigma.app.data.*
import ir.enigma.app.ui.group.component.GroupItem
import ir.enigma.app.ui.main.component.MainTopBar
import ir.enigma.app.ui.theme.SpaceMedium
import ir.enigma.app.R
import ir.enigma.app.ui.theme.IconLarge

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    var loading by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading)
            CircularProgressIndicator()


        Card(elevation = 2.dp) {
            MainTopBar(
                Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium),
                me,
                32000.0,
                12000.0,
                "تومان"
            )
        }
        LVSpacer()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(modifier = Modifier.padding(all = SpaceMedium)) {
                TextBody2(text = "گروه ها")
                LazyColumn(modifier = Modifier.padding(top = 15.dp)) {
                    itemsIndexed(fakeGroups) { index, item ->
                        GroupItem(
                            group = item,
                            ItrueVar = true,
                            Iamount = 16000.0,
                            Icurrency = "تومان"
                        )
                        SVSpacer()
                        if (index != fakeGroups.size - 1) {
                            Divider(startIndent = 55.dp)
                            SVSpacer()
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    CardWithImageOrIcon(
                        modifier = Modifier.align(Alignment.BottomStart),
                        icon = true,
                        resource = R.drawable.ic_fill_add,
                        size = IconLarge,
                        tint = MaterialTheme.colors.onPrimary,
                        backgroundColor = MaterialTheme.colors.primary,
                        elevation = 4.dp
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun pr() {
    RtlThemePreview {
        MainScreen(rememberNavController() , hiltViewModel());
    }
}