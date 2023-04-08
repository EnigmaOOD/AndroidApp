package ir.enigma.app.model

import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import java.util.Date


data class Purchase(
    val title: String,
    val date: Date,
    val totalPrice: Double,
    val sender: User,
    val purchaseCategory: PurchaseCategory,
    val buyers: List<Contribution>,
    val consumers: List<Contribution>,
) {

    fun getPersianDate(): PersianDateImpl {
        val persianDateImpl = PersianDateImpl()
        persianDateImpl.setDate(date)
        return persianDateImpl
    }
}

data class Contribution(val user: User, val price: Double)