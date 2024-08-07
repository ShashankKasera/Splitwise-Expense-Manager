package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var name: String?,
    var emailId: String?,
    var number: String?,
    var gender: String?
)
