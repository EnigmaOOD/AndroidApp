package ir.enigma.app.model

import com.google.gson.annotations.SerializedName
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


data class Purchase(
    @SerializedName("description")
    val title: String?,
    val date: String,
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

        persianDateImpl.setDate(
            LocalDate.parse(
                date
            ).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()
        )
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
) {

    override fun equals(other: Any?): Boolean {
        return user == other
    }

    override fun hashCode(): Int {
        return user.hashCode()
    }
}