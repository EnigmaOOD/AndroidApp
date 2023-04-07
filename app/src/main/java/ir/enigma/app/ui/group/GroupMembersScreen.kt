package ir.enigma.app.ui.group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.fakeUsers
import ir.enigma.app.ui.theme.*

@Composable
fun GroupMembersScreen(navController: NavController) {
    Surface(color = MaterialTheme.colors.primary) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextH5(text = "fdfd")
            TextH5(text = "fdfd")
            TextH5(text = "fdfd")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(modifier = Modifier.padding(horizontal = SpaceMedium, vertical = SpaceSmall)) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(modifier = Modifier.size(IconSmall),painter = painterResource(id = R.drawable.ic_profile_add), contentDescription = null)
                        THSpacer()
                        TextSubtitle1(text = "افزودن عضو جدید", color = MaterialTheme.colors.primary)
                    }
                    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                        itemsIndexed(fakeUsers) { index, item ->
                            UserItem(
                                user = item,
                                trueVar = true,
                                amount = 16000.0,
                                currency = "تومان"
                            )
                            SVSpacer()
                            if (index != fakeUsers.size - 1) {
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

@Preview
@Composable
fun p() {
    RtlThemePreview {
        GroupMembersScreen(rememberNavController())
    }
}