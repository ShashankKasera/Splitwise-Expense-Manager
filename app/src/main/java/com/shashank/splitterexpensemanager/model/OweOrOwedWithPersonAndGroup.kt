package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class OweOrOwedWithPersonAndGroup(
    val oweOrOwed: OweOrOwed,
    val personOwe: Person,
    val personOwed: Person,
    val group: Group
) : Parcelable