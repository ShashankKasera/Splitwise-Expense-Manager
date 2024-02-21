package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupMember(
    var id: Long?,
    var personId: Long,
    var groupId: Long,
) : Parcelable