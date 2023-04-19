package ir.enigma.app.ui.group

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.me
import ir.enigma.app.model.Group
import ir.enigma.app.model.GroupCategory
import ir.enigma.app.ui.group.component.PurchaseCard
import ir.enigma.app.ui.theme.*

@Composable
fun GroupScreen(navController: NavController, groupViewModel: GroupViewModel) {
    val group = groupViewModel.group
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                GroupProfile(group = group)
                SHSpacer()
                Column(modifier = Modifier.weight(1f)) {
                    TextH6(group.name, color = MaterialTheme.colors.onPrimary)
                    OnPrimaryHint(group.users.size.toString() + " عضو")
                }
                EasyIconButton(
                    iconId = R.drawable.ic_filter,
                    tint = MaterialTheme.colors.onPrimary,
                    size = IconMedium,
                    onClick = {
                        //TODO: Filter
                    }
                )
            }
        },

        ) { it ->
        val topPaddingValues = it.calculateTopPadding()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(groupViewModel.purchases) { thisPurchase ->
                SVSpacer()
                PurchaseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpaceMedium),
                    thisPurchase,
                    me,
                    group.currency,
                    onSenderClick = {},
                    onClick = {
                        //TODO: show all details of purchase
                    }
                )
                SVSpacer()
            }
        }
    }
}

@Composable
fun GroupProfile(group: Group) {
    CardWithImageOrIcon(
        icon = true,
        resource = GroupCategory.values()[group.categoryId].iconRes,
        contentPadding = 10.dp,
        size = IconLarge,
        tint = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = .2f),
        contentDescription = group.name,
    )
}

@Preview
@Composable
fun GroupScreenPreview() {
    RtlThemePreview {
        GroupScreen(rememberNavController(), GroupViewModel())
    }
}
