package ir.enigma.app.model

import com.google.gson.annotations.SerializedName
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import kotlin.properties.Delegates


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

    // builder class
    class Builder {
        var title: String? = null
        lateinit var date: String
        var totalPrice by Delegates.notNull<Double>()
        lateinit var sender: User
        var purchaseCategoryIndex by Delegates.notNull<Int>()
        var buyers: List<Contribution> = emptyList()
        var consumers: List<Contribution> = emptyList()
        fun title(title: String?) = apply { this.title = title }
        fun date(date: String) = apply { this.date = date }
        fun totalPrice(totalPrice: Double) = apply { this.totalPrice = totalPrice }
        fun sender(sender: User) = apply { this.sender = sender }
        fun purchaseCategoryIndex(purchaseCategoryIndex: Int) = apply { this.purchaseCategoryIndex = purchaseCategoryIndex }
        fun buyers(buyers: List<Contribution>) = apply { this.buyers = buyers }
        fun consumers(consumers: List<Contribution>) = apply { this.consumers = consumers }

        fun build() = Purchase(title, date, totalPrice, sender, purchaseCategoryIndex, buyers, consumers)
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