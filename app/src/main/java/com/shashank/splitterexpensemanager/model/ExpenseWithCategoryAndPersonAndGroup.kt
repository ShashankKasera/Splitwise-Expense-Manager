package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseWithCategoryAndPersonAndGroup(
    val expense: Expenses,
    val category: Category,
    val person: Person,
    val group: Group
) : Parcelable