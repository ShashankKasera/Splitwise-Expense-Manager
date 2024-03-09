package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupSettings(
    val group: Group = Group(),
    val groupMembersHashMap: HashMap<Person, Double> = hashMapOf(),
) : Parcelable