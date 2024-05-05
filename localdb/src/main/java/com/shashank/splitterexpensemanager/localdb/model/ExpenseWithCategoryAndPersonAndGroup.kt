package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExpenseWithCategoryAndPersonAndGroup(
    @Embedded
    val expense: Expenses?,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category?,
    @Relation(
        parentColumn = "personId",
        entityColumn = "id"
    )
    val person: Person?,

    @Relation(
        parentColumn = "groupId",
        entityColumn = "id"
    )
    val group: Group?
)