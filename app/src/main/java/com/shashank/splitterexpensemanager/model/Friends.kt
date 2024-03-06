package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friends(
    val person: Person = Person(),
    val friendsHashMap: HashMap<Person, Double> = hashMapOf(),
    val overallOweOrOwed: Double = 0.0,
) : Parcelable