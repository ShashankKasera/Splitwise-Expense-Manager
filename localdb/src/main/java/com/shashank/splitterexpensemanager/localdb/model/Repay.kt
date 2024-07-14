package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["payerId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["receiverId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Repay(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val payerId: Long?,
    val receiverId: Long?,
    val groupId: Long?,
    val amount: Double?,
    val date: String?,
    val time: String?,
    val description: String?,
)