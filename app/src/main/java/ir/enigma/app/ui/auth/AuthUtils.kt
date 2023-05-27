package ir.enigma.app.ui.auth

import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
import java.util.regex.Matcher
import java.util.regex.Pattern


fun isValidEmailAddress(email: String): Boolean {
    val ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = Pattern.compile(ePattern)
    val m = p.matcher(email)
    val isValid = m.matches()
    MyLog.log(
        StructureLayer.Util,
        "AuthUtils",
        "isValidEmailAddress",
        LogType.Info,
        "email = $email  validation result = $isValid"
    )
    return isValid
}
