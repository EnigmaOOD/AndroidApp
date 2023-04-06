package ir.enigma.app.ui.group.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.component.*
import ir.enigma.app.data.*
import ir.enigma.app.model.*
import ir.enigma.app.ui.group.util.calculateUserContribution
import ir.enigma.app.ui.theme.SpaceMedium
import ir.enigma.app.ui.theme.SpaceSmall
import ir.enigma.app.ui.theme.SpaceThin
import ir.enigma.app.ui.theme.onBackgroundAlpha7
import ir.enigma.app.util.toPrice
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurchaseCard(
    modifier: Modifier = Modifier,
    purchase: Purchase,
    me: User,
    currency: String,
    onSenderClick: () -> Unit,
    onClick: () -> Unit,
) {
    val category = purchase.purchaseCategory
    Card(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background,
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(horizontal = SpaceMedium)) {
            SVSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
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
                        MyContribution(me, purchase)
                    }
                    HintText(getPurchaseHint(purchase, currency))
                }


            }
            TVSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HintText("اضافه شده توسط ")

                HintText(
                    modifier = Modifier
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
fun MyContribution(me: User, purchase: Purchase) {
    val contribution = calculateUserContribution(me, purchase)

    val contributionText: String
    val color: Color
    if (contribution > 0) {
        contributionText = contribution.toPrice() + "+"
        color = MaterialTheme.colors.secondary
    } else if (contribution < 0) {
        contributionText = contribution.toPrice()
        color = MaterialTheme.colors.error
    } else {
        contributionText = "0"
        color = MaterialTheme.colors.onBackgroundAlpha7
    }
    TextBody2(
        text = contributionText,
        color = color,
    )

}

@Composable
fun getPurchaseHint(purchase: Purchase, currency: String): String {
    val isMeInBuyers = isMeInContributors(me, purchase.buyers)
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
            buyersText = "شما" + " و " + getOtherUser(me, purchase.buyers)!!.name
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
    return buyersText + " " + purchase.totalPrice.toPrice(currency) + " پرداخت " + verb

}


fun isMeInContributors(me: User, contributions: List<Contribution>): Boolean {
    contributions.forEach {
        if (me == it.user)
            return true
    }
    return false
}

fun getOtherUser(me: User, contribution: List<Contribution>): User? {
    contribution.forEach {
        if (me != it.user)
            return it.user
    }
    return null
}

@Preview(showBackground = false)
@Composable
fun PurchasePreview() {
    RtlThemePreview {

        PurchaseCard(
            purchase = Purchase(
                title = "پیتزا",
                date = Date(System.currentTimeMillis()),
                totalPrice = 25000.0,
                sender = userE,
                purchaseCategory = PurchaseCategory.WarmFood,
                buyers = listOf(Contribution(userA, .5), Contribution(userC, .5)),
                consumers = listOf(
                    Contribution(me, 1.0 / 3),
                    Contribution(userC, 1.0 / 3),
                    Contribution(userD, 1.0 / 3)
                )
            ),
            me = me,
            currency = "تومان",
            onClick = {},
            onSenderClick = {})


    }
}
