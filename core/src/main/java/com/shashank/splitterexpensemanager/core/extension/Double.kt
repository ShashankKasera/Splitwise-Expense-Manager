package com.shashank.splitterexpensemanager.core.extension

fun Double.formatNumber(decimalDigits: Int): String {
    return String.format("%.${decimalDigits}f", this)
}