package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var groupName: String,
    var groupType: String,
    var groupImage: String,
)
