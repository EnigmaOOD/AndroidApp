package ir.enigma.app.ui.group.util

import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Purchase
import ir.enigma.app.model.User
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer

fun calculateUserContribution(user: User, purchase: Purchase): Double {
    val contribution = calculateUserContribution(user, purchase.buyers) -
            calculateUserContribution(user, purchase.consumers)
    MyLog.log(
        StructureLayer.Util,
        "Function",
        "calculateUserContribution",
        LogType.Info,
        "calculateUserContribution: $contribution",
    )
    return contribution
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