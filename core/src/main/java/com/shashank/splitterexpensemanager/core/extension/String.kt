package com.shashank.splitterexpensemanager.core.extension

val String.Companion.EMPTY: String get() = ""

fun String.shortenName(fullName: String): String {
    val names = fullName.split(" ")
    return if (names.size >= 2) {
        val firstName = names.first()
        val lastName = names.last()
        "$firstName ${lastName.first()}."
    } else {
        fullName // Return the original name if it doesn't have a last name
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
    // Define your username criteria here
    val usernameRegex = "^[a-zA-Z]+\$".toRegex()

    // Check if the username matches the regex pattern
    return username.matches(usernameRegex)
}


