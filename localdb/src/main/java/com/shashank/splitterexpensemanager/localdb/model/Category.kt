package com.shashank.splitterexpensemanager.localdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var categoryName: String,
    var categoryImage: Int
)