package com.stylist.app.ui.auth

import java.util.regex.Matcher
import java.util.regex.Pattern

fun convertToEnglishNumbers(str: String): String {
    var result = ""
    var en: Char
    for (ch in str) {
        en = ch
        when (ch) {
            '۰' -> en = '0'
            '۱' -> en = '1'
            '۲' -> en = '2'
            '۳' -> en = '3'
            '۴' -> en = '4'
            '۵' -> en = '5'
            '۶' -> en = '6'
            '۷' -> en = '7'
            '۸' -> en = '8'
            '۹' -> en = '9'
        }
        result = "${result}$en"
    }
    return result
}

fun isValidEmailAddress(email: String): Boolean {
    val ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = Pattern.compile(ePattern)
    val m = p.matcher(email)
    return m.matches()
}

fun isValidPhoneNumber(s: String): Boolean {

    val VALID_PHONE_NUMBER = Pattern.compile("^0?9[0|1|2|3|9][0-9]{8}$")
    val m: Matcher = VALID_PHONE_NUMBER.matcher(s)
    return m.matches()
}