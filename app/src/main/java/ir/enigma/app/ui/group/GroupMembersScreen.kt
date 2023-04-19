package ir.enigma.app.ui.group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.me
import ir.enigma.app.model.GroupCategory
import ir.enigma.app.ui.theme.*

@Composable
fun GroupMembersScreen(navController: NavController, groupViewModel: GroupViewModel) {
    Surface(color = MaterialTheme.colors.primary) {
        val group = groupViewModel.group
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })

                CardWithImageOrIcon(
                    icon = true,
                    resource = GroupCategory.values()[group.categoryId].iconRes,
                    contentPadding = 13.dp,
                    size = IconSemiLarge,
                    tint = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = .2f),
                    contentDescription = group.name,
                )

                SHSpacer()
                Column(modifier = Modifier.weight(1f)) {
                    TextH6(group.name, color = MaterialTheme.colors.onPrimary)
                    OnPrimaryHint(group.users.size.toString() + " عضو")
                    OnPrimaryHint("واحد پولی: " + group.currency)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium,
                        vertical = SpaceSmall
                    )
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(IconSmall),
                            painter = painterResource(id = R.drawable.ic_profile_add),
                            contentDescription = null
                        )
                        THSpacer()
                        TextSubtitle1(
                            text = "افزودن عضو جدید",
                            color = MaterialTheme.colors.primary
                        )
                    }


//                    Divider(startIndent = 55.dp)
                    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                        item {
                            UserItem(
                                user = me,
                                trueVar = true,
                                isMe = true,
                                amount = 16000.0,
                                currency = "تومان"
                            )
                            SVSpacer()
                            if (group.users.size > 1) {
                                Divider(startIndent = 55.dp)
                                SVSpacer()
                            }
                        }
                        itemsIndexed(group.users) { index, item ->
                            if (item != me) {
                                UserItem(
                                    user = item,
                                    trueVar = true,
                                    amount = 16000.0,
                                    currency = "تومان"
                                )
                                SVSpacer()
                                if (index != group.users.size - 1) {
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
fun p() {
    RtlThemePreview {
        GroupMembersScreen(rememberNavController(), GroupViewModel())
    }
}