package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Embedded
import androidx.room.Relation

data class OweOrOwedWithPerson(
    @Embedded val oweOrOwed: OweOrOwed?,
    @Relation(
        parentColumn = "personOwedId",
        entityColumn = "id"
    )
    val personOwed: Person?,

    @Relation(
        parentColumn = "personOweId",
        entityColumn = "id"
    )
    val personOwe: Person?,
)