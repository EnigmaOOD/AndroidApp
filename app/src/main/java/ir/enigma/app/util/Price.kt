package ir.enigma.app.util

import kotlin.math.abs
import kotlin.math.roundToInt

fun Double.toPrice(currency: String? = null): String {
    if (this == 0.0)
        return "0"
    val builder = StringBuilder()
    val negative = this < 0
    var value = abs(this.toInt())
    val floatPart: Double = ((abs(this) - value) * 100.0).roundToInt() / 100.0
    if (floatPart > 0) {
        if (value == 0)
            builder.append(floatPart.toString())
        else
            builder.append(floatPart.toString().removeRange(0, 1))
        builder.reverse()
    }

    var i = 0
    while (value > 0) {
        val r = value % 10
        builder.append(r)
        value /= 10
        if (i == 2 && value > 0) {
            builder.append(',')
            i = -1
        }
        i++

    }
    val final = builder.reverse().toString()
    if (negative)
        return "$final-" + if (currency != null) " $currency" else ""
    return final + if (currency != null) " $currency" else ""
}

fun String.zeroIfEmpty(): String {
    return if (this.isEmpty()) "0" else this
}
