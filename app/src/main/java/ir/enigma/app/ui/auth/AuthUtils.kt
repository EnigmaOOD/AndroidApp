package ir.enigma.app.ui.auth

import java.util.regex.Matcher
import java.util.regex.Pattern



fun isValidEmailAddress(email: String): Boolean {
    val ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = Pattern.compile(ePattern)
    val m = p.matcher(email)
    return m.matches()
}
