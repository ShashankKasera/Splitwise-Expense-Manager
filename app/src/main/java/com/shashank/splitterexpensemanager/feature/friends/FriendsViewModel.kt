package com.shashank.splitterexpensemanager.feature.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.friends.repository.FriendsRepository
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import com.shashank.splitterexpensemanager.model.Friends
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    var friendsRepository: FriendsRepository
) : ViewModel() {

    private val _allFriends = MutableStateFlow(Pair<List<Friends>, Double>(listOf(), 0.0))
    val allFriends = _allFriends.asStateFlow()

    fun loadAllFriends(personId: Long) {
        var youOverallOweOrOwed = 0.0

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allPersonDeferred = async { friendsRepository.getAllPersonsExcept(personId) }
                val friendList = mutableListOf<Friends>()
                allPersonDeferred.await().forEach { friend ->
                    val groupDeferred =
                        async { friendsRepository.loadAllGroupByFriendId(friend.id ?: -1) }
                    var amount = 0.0
                    val friendOweOwedList = mutableListOf<FriendOweOrOwed>()

                    groupDeferred.await().forEach { group ->

                        val oweDeferred = async {
                            friendsRepository.loadAllOweByOweIdAndOwedId(
                                friend.id ?: -1,
                                personId,
                                group.id ?: -1
                            )
                        }
                        val owedDeferred = async {
                            friendsRepository.loadAllOwedByOweIdAndOwedId(
                                personId,
                                friend.id ?: -1,
                                group.id ?: -1
                            )
                        }
                        val owe = oweDeferred.await()
                        val owed = owedDeferred.await()

                        if ((owed - owe) != 0.0) {
                            friendOweOwedList.add(
                                FriendOweOrOwed(
                                    friend,
                                    group,
                                    (owed - owe)
                                )
                            )
                        }
                        amount += (owed - owe)
                    }
                    youOverallOweOrOwed += amount
                    friendList.add(
                        Friends(
                            friend = friend,
                            friendsOweOwedList = friendOweOwedList,
                            overallOweOrOwed = amount
                        )
                    )
                }

                _allFriends.emit(Pair(friendList, youOverallOweOrOwed))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}