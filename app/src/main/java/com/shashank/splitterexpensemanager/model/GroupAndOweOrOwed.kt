package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupAndOweOrOwed(
    val group: Group = Group(),
    val hashMap: HashMap<Person, Double> = hashMapOf(),
    val overall: Double = 0.0
) : Parcelable