package ir.enigma.app.ui.group.util

import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User

fun calculateUserContribution(user: User, purchase: Purchase): Double {
    return calculateUserContribution(user, purchase.buyers) -
            calculateUserContribution(user, purchase.consumers)
}

private fun calculateUserContribution(
    user: User,
    contributions: List<Contribution>
): Double {
    contributions.forEach {
        if (it.user == user) {
            return it.price
        }
    }
    return 0.0
}