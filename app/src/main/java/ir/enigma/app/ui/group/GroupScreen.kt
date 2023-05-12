package ir.enigma.app.ui.group

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
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
import ir.enigma.app.model.PurchaseCategory
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.group.component.*
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
    val showingPurchase = remember { mutableStateOf<Purchase?>(null) }
    LaunchedEffect(Unit) {
        groupViewModel.fetchGroupData(groupId)
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colors.primary
    )

    val interactionSource = remember { MutableInteractionSource() }

    if (showingPurchase.value != null)
        PurchaseFullDetailsDialog(showingPurchase.value!!, group!!.currency, onDismiss = {
            showingPurchase.value = null
        })

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
                            onClick = {
                                showingPurchase.value = thisPurchase
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
fun PurchaseFullDetailsDialog(purchase: Purchase, currency: String, onDismiss: () -> Unit) {
    val category = PurchaseCategory.values()[purchase.purchaseCategoryIndex]
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card {
            Column(modifier = Modifier.padding(SpaceLarge)) {
                SVSpacer()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CategoryIcon(category)
                    SHSpacer()
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextH6(
                                modifier = Modifier.weight(1f),
                                text = purchase.title ?: category.text
                            )
                            MyContribution(me, purchase)
                        }
                        TextBody2(
                            text = purchase.totalPrice.toPrice(currency),
                            color = colors.onSurface.copy(alpha = .5f)
                        )
                    }


                }
                // purchase buyers column
                MVSpacer()

                TextH6("خریداران")
                SVSpacer()
                purchase.buyers.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HintText(it.user.name)
                        Spacer(modifier = Modifier.weight(1f))
                        HintText(it.price.toPrice(currency))
                    }
                    SVSpacer()
                }

                TextH6("مصرف کنندگان")
                SVSpacer()
                purchase.consumers.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HintText(it.user.name)
                        Spacer(modifier = Modifier.weight(1f))
                        HintText(it.price.toPrice(currency))
                    }
                    SVSpacer()
                }


                LVSpacer()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HintText("اضافه شده توسط ")

                    HintText(

                        text = purchase.sender.name,
                        color = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    val pDate = purchase.getPersianDate()
                    HintText(pDate.persianDay.toString() + " " + pDate.persianMonthName)
                }
            }

        }
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
