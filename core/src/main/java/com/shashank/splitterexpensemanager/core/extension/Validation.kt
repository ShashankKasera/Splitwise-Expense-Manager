package com.shashank.splitterexpensemanager.core.extension

import android.text.TextUtils
import android.util.Patterns
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"

fun String.isValidEmail(): Boolean =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean {
    return !TextUtils.isEmpty(this) && length >= 8 && contains(Regex(PASSWORD_REGEX))
}

fun String.isValidPhoneNumber(): Boolean = length == 10

fun String.isValidDisplayName(): Boolean {
    var regex = "^[A-Za-z0-9_]+$"

    val p: Pattern = Pattern.compile(regex)
    if (this.isEmpty()) return false

    val m: Matcher = p.matcher(this)
    return if (!m.matches()) false else this.length < 25
}

fun String.checkWhiteSpaces(): Boolean {
    return !this.trim().isNotEmpty()
}

fun Calendar.isValidBirthDayDate(): Boolean {
    val today = Calendar.getInstance()
    val year = -18
    today.add(Calendar.YEAR, year)
    val year18Ago = today.time
    val selectedDate = this.time
    return selectedDate < year18Ago
}