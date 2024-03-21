package com.shashank.splitterexpensemanager.feature.groupsettledup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.friendsdetails.repository.FriendsDetailsRepository
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupSettledUpViewModel @Inject constructor(
    private val friendsDetailsRepository: FriendsDetailsRepository
) : ViewModel() {

    private val _allFriends = MutableStateFlow<List<FriendOweOrOwed>>(listOf())
    val allFriends = _allFriends.asStateFlow()
    fun loadAllFriends(personId: Long, friendId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val friendDeferred =
                    async { friendsDetailsRepository.loadFriendByFriendId(friendId) }
                val groupDeferred =
                    async { friendsDetailsRepository.loadAllGroupByFriendId(friendId) }

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

                    if ((owed - owe) != 0.0) {
                        friendOweOwedList.add(
                            FriendOweOrOwed(
                                friendDeferred.await(),
                                it,
                                (owed - owe)
                            )
                        )
                    }
                }

                _allFriends.emit(friendOweOwedList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}