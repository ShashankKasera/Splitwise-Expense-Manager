package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OweOrOwed(
    var id: Long?,
    var personOweId: Long,
    var personOwedId: Long,
    var groupId: Long,
    var amount: Double,
) : Parcelable