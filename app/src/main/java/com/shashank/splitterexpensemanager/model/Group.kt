package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    var id: Long?,
    var groupName: String,
    var groupType: String,
    var groupImage: String,
) : Parcelable