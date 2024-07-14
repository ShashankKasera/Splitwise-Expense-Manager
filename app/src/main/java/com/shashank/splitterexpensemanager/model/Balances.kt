package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class Balances(
    val person: Person = Person(),
    val oweOwedList: List<Pair<Person, Double>> = listOf(),
    val amount: Double = 0.0
) : Parcelable