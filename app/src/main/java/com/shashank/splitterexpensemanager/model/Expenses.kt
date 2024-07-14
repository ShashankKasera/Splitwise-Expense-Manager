package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expenses(
    var id: Long,
    var personId: Long,
    var groupId: Long,
    var categoryId: Long,
    var amount: Double,
    var splitAmount: Double,
    var name: String,
    var date: String,
    var time: String,
    var description: String,
) : Parcelable