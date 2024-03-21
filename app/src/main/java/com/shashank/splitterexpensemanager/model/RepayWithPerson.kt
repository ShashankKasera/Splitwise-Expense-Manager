package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepayWithPerson(
    val repay: Repay,
    val payer: Person,
    val receiver: Person,
) : Parcelable