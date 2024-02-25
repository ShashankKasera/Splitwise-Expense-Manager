package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupDetails(
    var expenses: List<ExpenseWithCategoryAndPerson> = listOf(),
    var group: Group = Group(),
    var groupMember: List<Person> = listOf(),
    var overallOweOrOwed: Double = 0.0,
    val hashMap: HashMap<Person, Double> = hashMapOf()
) : Parcelable