package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Expenses(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var personId: Long,
    var groupId: Long,
    var categoryId: Long,
    var amount: Double,
    var splitAmount: Double,
    var name: String,
    var date: String,
    var time: String,
    var description: String,
)