package com.shashank.splitterexpensemanager.feature.friendsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.friendsdetails.repository.FriendsDetailsRepository
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import com.shashank.splitterexpensemanager.model.FriendsDetails
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsDetailsViewModel @Inject constructor(
    private val friendsDetailsRepository: FriendsDetailsRepository
) : ViewModel() {

    private val _allFriends = MutableStateFlow<FriendsDetails>(FriendsDetails())
    val allFriends = _allFriends.asStateFlow()
    fun loadAllFriends(personId: Long, friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val friendDeferred =
                    async { friendsDetailsRepository.loadFriendByFriendId(friendId) }
                val groupDeferred =
                    async { friendsDetailsRepository.loadAllGroupByFriendId(friendId) }

                val groupOweOwedHashmap: HashMap<Group, Double> =
                    HashMap(groupDeferred.await().associate { it to 0.0 })
                var amount = 0.0

                val friendOweOwedList = mutableListOf<FriendOweOrOwed>()
                groupDeferred.await().forEach {
                    val oweDeferred = async {
                        friendsDetailsRepository.loadAllOweByOweIdAndOwedId(
                            friendId,
                            personId,
                            it.id ?: -1
                        )
                    }
                    val owedDeferred = async {
                        friendsDetailsRepository.loadAllOwedByOweIdAndOwedId(
                            personId,
                            friendId,
                            it.id ?: -1
                        )
                    }
                    val owe = oweDeferred.await()
                    val owed = owedDeferred.await()
                    groupOweOwedHashmap[it] = owed - owe
                    if ((owed - owe) != 0.0) {
                        if ((owed - owe) < 0.0) {
                            friendOweOwedList.add(
                                FriendOweOrOwed(
                                    friendDeferred.await(),
                                    it,
                                    (owed - owe)
                                )
                            )
                        } else {
                            friendOweOwedList.add(
                                FriendOweOrOwed(
                                    friendDeferred.await(),
                                    it,
                                    (owed - owe)
                                )
                            )
                        }
                    }
                    amount += (owed - owe)
                }

                val data = FriendsDetails(
                    friend = friendDeferred.await(),
                    friendsHashMap = groupOweOwedHashmap,
                    friendOweOwedList = friendOweOwedList,
                    overallOweOrOwed = amount
                )
                _allFriends.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}