package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Expenses::class,
            parentColumns = ["id"],
            childColumns = ["expensesId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Repay::class,
            parentColumns = ["id"],
            childColumns = ["repayId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personOwedId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personOweId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OweOrOwed(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val expensesId: Long?,
    val repayId: Long?,
    val personOwedId: Long?,
    val personOweId: Long?,
    val groupId: Long?,
    val amount: Double?,
)
