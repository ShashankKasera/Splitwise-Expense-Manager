package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendOweOrOwed(
    val owe: Person = Person(),
    val owed: Person = Person(),
    val group: Group = Group(),
    val groupOweOwed: Double = 0.0,
) : Parcelable