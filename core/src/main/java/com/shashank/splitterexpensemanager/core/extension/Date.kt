package com.shashank.splitterexpensemanager.core.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Date.dateToString(date: Date?, format: String): String {
    val sdf = SimpleDateFormat(format)
    return sdf.format(date)
}