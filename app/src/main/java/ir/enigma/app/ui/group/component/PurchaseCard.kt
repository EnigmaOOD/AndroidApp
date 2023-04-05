package ir.enigma.app.ui.group.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.enigma.app.component.*
import ir.enigma.app.data.*
import ir.enigma.app.data.UserRepository.Companion.getMe
import ir.enigma.app.data.UserRepository.Companion.isMe
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.PurchaseCategory
import ir.enigma.app.model.User
import ir.enigma.app.ui.group.util.calculateUserContribution
import ir.enigma.app.ui.theme.EnigmaAppTheme
import ir.enigma.app.ui.theme.SpaceMedium
import ir.enigma.app.ui.theme.SpaceSmall
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.util.toPrice
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurchaseCard(
    modifier: Modifier = Modifier,
    purchase: Purchase,
    currency: String,
    onSenderClick: () -> Unit,
    onClick: () -> Unit,
) {
    val category = purchase.purchaseCategory
    Card(
        modifier = modifier,
        elevation = 0.dp,
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(SpaceMedium)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SquircleIcon(
                    iconId = category.iconRes,
                    backgroundColor = category.categoryGroup.color,
                    iconTint = Color.Black.copy(alpha = .9f)
                )
                SHSpacer()
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextH6(modifier = Modifier.weight(1f), text = purchase.title)
                        MyContribution(purchase)
                    }
                    HintText(getPurchaseHint(purchase, currency))
                }


            }
            SVSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HintText("اضافه شده توسط")

                TextBody1(
                    modifier = Modifier.padding(horizontal = SpaceThin)
                        .clickable(onClick = onSenderClick),
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

@Composable
fun MyContribution(purchase: Purchase) {
    val contribution = calculateUserContribution(getMe(), purchase)
    val contributionText: String
    val color: Color
    if (contribution > 0) {
        contributionText = contribution.toInt().toPrice() + "+"
        color = MaterialTheme.colors.secondary
    } else {
        contributionText = contribution.toInt().toPrice()
        color = MaterialTheme.colors.error
    }
    TextBody2(
        text = contributionText,
        color = color,
    )
}

@Composable
fun getPurchaseHint(purchase: Purchase, currency: String): String {
    val isMeInBuyers = isMeInContributors(purchase.buyers)
    val buyersCount = purchase.buyers.size
    val verb: String
    val buyersText: String
    if (buyersCount > 2) {
        if (isMeInBuyers) {
            buyersText = "شما و $buyersCount نفر دیگر"
            verb = "کردید"
        } else {
            buyersText = purchase.buyers[0].user.name + " و " + buyersCount + "نفر دیگر"
            verb = "کردند"
        }

    } else if (buyersCount == 2) {
        if (isMeInBuyers) {
            buyersText = "شما" + " و " + getOtherUser(purchase.buyers)!!.name
            verb = "کردید"
        } else {
            buyersText = purchase.buyers[0].user.name + " و " + purchase.buyers[1].user.name
            verb = "کردند"
        }
    } else {
        if (isMeInBuyers) {
            buyersText = "شما"
            verb = "کردید"
        } else {
            buyersText = purchase.buyers[0].user.name
            verb = "کرد"
        }
    }
    return buyersText + " " + purchase.totalPrice + " " + currency + " پرداخت " + verb

}


fun isMeInContributors(contributions: List<Contribution>): Boolean {
    contributions.forEach {
        if (isMe(it.user))
            return true
    }
    return false
}

fun getOtherUser(contribution: List<Contribution>): User? {
    contribution.forEach {
        if (!isMe(it.user))
            return it.user
    }
    return null
}

@Preview(showBackground = false)
@Composable
fun PurchasePreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        EnigmaAppTheme {
            PurchaseCard(
                purchase = Purchase(
                    title = "پیتزا",
                    date = Date(System.currentTimeMillis()),
                    totalPrice = 25000,
                    sender = userE,
                    purchaseCategory = PurchaseCategory.WarmFood,
                    buyers = listOf(Contribution(userA, .5), Contribution(me, .5)),
                    consumers = listOf(
                        Contribution(me, 1.0 / 3),
                        Contribution(userC, 1.0 / 3),
                        Contribution(userD, 1.0 / 3)
                    )
                ),
                currency = "تومان",
                onClick = {},
                onSenderClick = {})
        }
    }
}