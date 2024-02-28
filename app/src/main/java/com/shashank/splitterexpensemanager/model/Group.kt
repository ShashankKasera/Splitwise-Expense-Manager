package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    var id: Long? = -1,
    var groupName: String = String.EMPTY,
    var groupType: String = String.EMPTY,
    var groupImage: String = String.EMPTY,
) : Parcelable