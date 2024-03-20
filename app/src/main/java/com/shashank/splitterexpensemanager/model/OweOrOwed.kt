package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OweOrOwed(
    var id: Long?,
    val expensesId: Long,
    val repayId: Long,
    var personOwedId: Long,
    var personOweId: Long,
    var groupId: Long,
    var amount: Double,
) : Parcelable