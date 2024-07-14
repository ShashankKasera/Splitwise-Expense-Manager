package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OweOrOwed(
    var id: Long = -1,
    val expensesId: Long = -1,
    val repayId: Long = -1,
    var personOwedId: Long = -1,
    var personOweId: Long = -1,
    var groupId: Long = -1,
    var amount: Double = 0.0,
) : Parcelable