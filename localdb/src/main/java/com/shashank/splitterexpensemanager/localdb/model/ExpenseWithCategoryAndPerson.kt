package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExpenseWithCategoryAndPerson(
    @Embedded val expense: Expenses,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category,
    @Relation(
        parentColumn = "personId",
        entityColumn = "id"
    )
    val person: Person
)