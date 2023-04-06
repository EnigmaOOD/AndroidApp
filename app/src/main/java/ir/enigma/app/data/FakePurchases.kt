package ir.enigma.app.data

import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.PurchaseCategory
import java.util.*

val purchaseA = Purchase(
    title = "اسنپ",
    date = Date(System.currentTimeMillis()),
    totalPrice = 12000.0,
    sender = me,
    purchaseCategory = PurchaseCategory.Taxi,
    buyers = listOf(Contribution(userA, 12000.0)),
    consumers = listOf(Contribution(me, 9000.0), Contribution(userC, 3000.0)),
)

val purchaseB = Purchase(
    title = "گیم نت",
    date = Date(System.currentTimeMillis()),
    totalPrice = 24000.0,
    sender = userA,
    purchaseCategory = PurchaseCategory.Game,
    buyers = listOf(Contribution(me, 20000.0), Contribution(userE, 4000.0)),
    consumers = listOf(
        Contribution(userA, 9000.0),
        Contribution(me, 3000.0),
        Contribution(userC, 12000.0)
    ),
)

val purchaseC = Purchase(
    title = "نیم پرس چلو کباب",
    date = Date(System.currentTimeMillis()),
    totalPrice = 12000.0,
    sender = userE,
    purchaseCategory = PurchaseCategory.WarmFood,
    buyers = listOf(
        Contribution(userD, 500.0),
        Contribution(userE, 500.0),
        Contribution(userC, 400.0),
        Contribution(userB, 400.0),
    ),
    consumers = listOf(
        Contribution(userB, 1800.0),
    ),
)

val purchaseD = Purchase(
    title = "پایان نامه",
    date = Date(System.currentTimeMillis()),
    totalPrice = 150000.0,
    sender = me,
    purchaseCategory = PurchaseCategory.Education,
    buyers = listOf(
        Contribution(me, 100000.0), Contribution(userA, 20000.0),
        Contribution(userD, 20000.0),
        Contribution(userE, 10000.0),
    ),
    consumers = listOf(Contribution(userB, 150000.0)),
)

val fakePurchases = {
    val list = arrayListOf(
        purchaseA,
        purchaseB,
        purchaseC,
        purchaseD
    )
    repeat(10) {
        list.addAll(list)
    }
    list

}
