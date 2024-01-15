package com.shashank.splitterexpensemanager.core.extension

import android.os.Bundle

fun <V> Map<String, V>.toBundle(bundle: Bundle = Bundle()): Bundle = bundle.apply {
    forEach {
        val k = it.key
        when (val v = it.value) {
            is Byte -> putByte(k, v)
            is Short -> putShort(k, v)
            is ShortArray -> putShortArray(k, v)
            is Char -> putChar(k, v)
            is CharSequence -> putCharSequence(k, v)
            is Float -> putFloat(k, v)
            is Boolean -> putBoolean(k, v)
            is java.io.Serializable -> putSerializable(k, v)
            else -> throw IllegalArgumentException("$v is of a type that is not currently supported")
        }
    }
}