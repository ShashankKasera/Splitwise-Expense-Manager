package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id: Long?,
    var categoryName: String,
    var categoryImage: Int
) : Parcelable
