package com.shashank.splitterexpensemanager.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendsDetails(
    val friend: Person = Person(),
    val friendsHashMap: HashMap<Group, Double> = hashMapOf(),
    val friendOweOwedList: List<FriendOweOrOwed> = listOf(),
    val overallOweOrOwed: Double = 0.0,
) : Parcelable