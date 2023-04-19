package ir.enigma.app.ui.group

import InputTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.component.*
import ir.enigma.app.data.me
import ir.enigma.app.ui.theme.SpaceThin

@Composable
fun NewPurchaseScreen(navController: NavController) {
    val description = remember { mutableStateOf("") }
    //جنسش چی باید باشه؟
    val for_ = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val isRelatedBuyer = remember { mutableStateOf(true) }
    val isRelatedConsumer = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                TextH6(text = "خرید جدید")
            }
        },
    ) { it ->
        val topPaddingValues = it.calculateTopPadding()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                elevation = 0.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    InputTextField(
                        text = description,
                        label = "توضیحات",
                    )
                    InputTextField(
                        text = for_,
                        label = "بابت",
                        onValueChange = { for_.value = it })
                    InputTextField(
                        text = price,
                        label = "قیمت",
                        onValueChange = { price.value = it })
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextH6(text = "خریدارها")
                        SegmentedControl(onItemSelection = {
                            isRelatedBuyer.value = !isRelatedBuyer.value
                        })
                    }

                    if (isRelatedBuyer.value) {
                        Row(modifier = Modifier.padding(bottom = 15.dp)) {
                            NewPurchase(user = me)
                        }
                        Row() {
                            AddOutlinedButton()
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextH6(text = "مصرف کننده ها")
                        SegmentedControl(onItemSelection = {
                            isRelatedConsumer.value = !isRelatedConsumer.value
                        })
                    }

                    if (isRelatedConsumer.value) {
                        Row(modifier = Modifier.padding(bottom = 15.dp)) {
                            NewPurchase(user = me)
                        }
                        Row() {
                            AddOutlinedButton()
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun f() {
    RtlThemePreview {
        NewPurchaseScreen(navController = rememberNavController())
    }
}