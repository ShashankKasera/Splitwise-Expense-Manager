package com.shashank.splitterexpensemanager.core.extension

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Activity.showToast(text: String, durationLong: Boolean = false) {
    applicationContext?.showToast(text, durationLong)
}

fun Activity.getBackgroundDrawable(@DrawableRes id: Int): Drawable? =
    ContextCompat.getDrawable(this, id)

fun Activity.getResolvedColor(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)