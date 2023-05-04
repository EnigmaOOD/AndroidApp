package ir.enigma.app.model

import com.google.gson.annotations.SerializedName
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import java.util.Date


data class Purchase(
    @SerializedName("description")
    val title: String?,
    val date: Date,
    @SerializedName("cost")
    val totalPrice: Double,
    @SerializedName("added_by")
    val sender: User,
    @SerializedName("picture_id")
    val purchaseCategoryIndex: Int,
    @SerializedName("Buyers")
    val buyers: List<Contribution>,

    val consumers: List<Contribution>,
) {

    val purchaseCategory: PurchaseCategory
        get() = PurchaseCategory.values()[
                if (purchaseCategoryIndex >= PurchaseCategory.values().size || purchaseCategoryIndex < 0)
                    PurchaseCategory.values().size - 1
                else
                    purchaseCategoryIndex
        ]


    fun getPersianDate(): PersianDateImpl {
        val persianDateImpl = PersianDateImpl()
        persianDateImpl.setDate(date)
        return persianDateImpl
    }
}

data class Contribution(
    @SerializedName("userID")
    val user: User,
    @SerializedName("percent")
    val price: Double
)

data class Member(
    @SerializedName("userID")
    val user: User,
    val cost: Double
)