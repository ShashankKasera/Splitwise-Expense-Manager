package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class OweOrOwedWithPerson(
    val oweOrOwed: OweOrOwed,
    val personOwed: Person,
    val personOwe: Person,
) : Parcelable