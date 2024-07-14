package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Embedded
import androidx.room.Relation

data class OweOrOwedWithPersonAndGroup(
    @Embedded val oweOrOwed: OweOrOwed?,
    @Relation(
        parentColumn = "personOweId",
        entityColumn = "id"
    )
    val personOwe: Person?,

    @Relation(
        parentColumn = "personOwedId",
        entityColumn = "id"
    )
    val personOwed: Person?,

    @Relation(
        parentColumn = "groupId",
        entityColumn = "id"
    )
    val group: Group?,
)
