package com.shashank.splitterexpensemanager.core.extension

val String.Companion.EMPTY: String get() = ""

fun String.shortenName(fullName: String): String {
    val names = fullName.split(" ")
    return if (names.size >= 2) {
        val firstName = names.first()
        val lastName = names.last()
        "$firstName ${lastName.first()}."
    } else {
        fullName
    }
}

fun String.capitalizeFirstLetter(input: String): String {
    return if (input.isNotBlank()) {
        input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase()
    } else {
        input
    }
}

fun String.isValidGmail(email: String): Boolean {
    val gmailPattern = "[a-zA-Z0-9._%+-]+@gmail\\.com"
    val regex = Regex(gmailPattern)
    return regex.matches(email)
}

fun String.isValidUserName(username: String): Boolean {
    val usernameRegex = "^[a-zA-Z\\s]+\$".toRegex()
    return username.matches(usernameRegex)
}


fun String.isValidMobileNumber(number: String): Boolean {
    val pattern = Regex("^\\d{10}\$")
    return pattern.matches(number)
}

