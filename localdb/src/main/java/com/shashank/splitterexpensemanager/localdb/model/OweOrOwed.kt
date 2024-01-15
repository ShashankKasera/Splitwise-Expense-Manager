package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personOweId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Person::class,
            parentColumns = ["id"],
            childColumns = ["personOwedId"],
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
    var id: Long,
    var personOweId: Long,
    var personOwedId: Long,
    var groupId: Long,
    var amount: Long,
)
