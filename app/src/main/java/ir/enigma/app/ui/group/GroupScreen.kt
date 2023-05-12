package ir.enigma.app.ui.group

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.enigma.app.R
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiStatus

import ir.enigma.app.model.Group
import ir.enigma.app.model.GroupCategory
import ir.enigma.app.model.Purchase
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.group.component.GroupButtonbar
import ir.enigma.app.ui.group.component.PurchaseCard
import ir.enigma.app.ui.main.component.ShimmerColumn
import ir.enigma.app.ui.main.component.ShimmerItem
import ir.enigma.app.ui.navigation.Screen
import ir.enigma.app.ui.theme.*
import ir.enigma.app.util.toPrice
import java.util.Currency

@Composable
fun GroupScreen(navController: NavController, groupViewModel: GroupViewModel, groupId: Int) {
    val status = groupViewModel.state.value.status
    val group = groupViewModel.state.value.data
    val purchases = groupViewModel.purchaseList.collectAsState().value

    LaunchedEffect(Unit) {
        groupViewModel.fetchGroupData(groupId)
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colors.primary
    )

    val interactionSource = remember { MutableInteractionSource() }
    ApiScreen(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = colors.background,
        apiResult = groupViewModel.state,
        topBar = {
            TopAppBar(
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (status == ApiStatus.SUCCESS)
                        navController.navigate(Screen.GroupMembersScreen.name)
                }, contentPadding = PaddingValues(vertical = SpaceThin)
            ) {
                BackIconButton(onClick = {
                    navController.popBackStack()
                })
                if (group == null)
                    ShimmerItem()
                else {
                    GroupProfile(group = group)
                    SHSpacer()
                    Column(modifier = Modifier.weight(1f)) {
                        TextH6(group.name, color = colors.onPrimary)
                        if (group.members == null)
                            ShimmerItem()
                        else
                            OnPrimaryHint(group.members!!.size.toString() + " عضو")
                    }
                }
                EasyIconButton(
                    iconId = R.drawable.ic_filter,
                    tint = colors.onPrimary,
                    size = IconMedium,
                    onClick = {
                        //TODO: Filter
                    }
                )
            }
        },

        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val topPaddingValues = it.calculateTopPadding()
            if (group == null || groupViewModel.purchaseState.value.status != ApiStatus.SUCCESS) {
                SVSpacer()

                ShimmerColumn(count = 20, circleSize = IconLarge)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().weight(1f),
                ) {
                    items(purchases) { thisPurchase ->
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


                GroupButtonbar(
                    amount = groupViewModel.meMember!!.cost,
                    currency = group.currency,
                    onLeaveGroup = {
                        groupViewModel.leaveGroup(group);
                    },
                    onSettleUp = { amount ->

                        navigateToNewPurchaseScreen(navController, amount)
                    }, onAddPurchase = {
                        navigateToNewPurchaseScreen(navController)
                    })

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
        tint = colors.onPrimary,
        backgroundColor = colors.onPrimary.copy(alpha = .2f),
        contentDescription = group.name,
    )
}

@Composable
fun PurchaseFullDetailsDialog(purchase: Purchase, currency: String) {
    Dialog(
        onDismissRequest = { },
    ) {


    }


}

fun navigateToNewPurchaseScreen(navController: NavController, amount: Double? = null) {
    Log.d("Screen", "GroupScreen navigateToNewPurchaseScreen: $amount")
    if (amount != null)
        navController.navigate(Screen.NewPurchaseScreen.name + "/$amount")
    else
        navController.navigate(Screen.NewPurchaseScreen.name + "/0.0")
}

@Preview
@Composable
fun GroupScreenPreview() {
    RtlThemePreview {
//        GroupScreen(rememberNavController(), GroupViewModel())
    }
}
