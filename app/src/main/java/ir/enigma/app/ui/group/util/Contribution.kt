package ir.enigma.app.ui.group.util

import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User

fun calculateUserContribution(user: User, purchase: Purchase): Double {
    return calculateUserContribution(user, purchase.buyers, purchase.totalPrice) -
            calculateUserContribution(user, purchase.consumers, purchase.totalPrice)
}

private fun calculateUserContribution(
    user: User,
    contributions: List<Contribution>,
    totalPrice: Int
): Double {
    contributions.forEach {
        if (it.user == user) {
            return it.percentOfTotal * totalPrice;
        }
    }
    return 0.0
}