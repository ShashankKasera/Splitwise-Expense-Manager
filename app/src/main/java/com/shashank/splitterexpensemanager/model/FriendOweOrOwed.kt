package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendOweOrOwed(

    val friend: Person = Person(),
    val group: Group = Group(),
    val groupOweOwed: Double = 0.0,
) : Parcelable