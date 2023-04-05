package ir.enigma.app.util

fun Int.toPrice(): String {
    val builder = StringBuilder()
    var value = this
    var i = 0
    while (value > 0) {
        val r = value % 10
        builder.append(r)
        value /= 10
        if (i == 2) {
            builder.append(',')
            i = -1
        }
        i++
    }
    return builder.reverse().toString()
}