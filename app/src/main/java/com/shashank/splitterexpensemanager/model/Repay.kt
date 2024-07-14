package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repay(
    val id: Long?,
    val payerId: Long?,
    val receiverId: Long?,
    val groupId: Long?,
    val amount: Double?,
    val date: String?,
    val time: String?,
    val description: String?,
) : Parcelable