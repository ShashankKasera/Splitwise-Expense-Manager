package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Embedded
import androidx.room.Relation

data class RepayWithPersonAndGroup(
    @Embedded
    val repay: Repay?,
    @Relation(
        parentColumn = "payerId",
        entityColumn = "id"
    )
    val payer: Person?,
    @Relation(
        parentColumn = "receiverId",
        entityColumn = "id"
    )
    val receiver: Person?,

    @Relation(
        parentColumn = "groupId",
        entityColumn = "id"
    )
    val group: Group?
)