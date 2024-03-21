package com.shashank.splitterexpensemanager.feature.friendsetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.friendsetting.repository.FriendSettingRepository
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendSettingViewModel @Inject constructor(
    private val friendSettingRepository: FriendSettingRepository,
) : ViewModel() {
    private val _allGroupList = MutableStateFlow<List<Group>>(listOf())
    val allGroupList = _allGroupList.asStateFlow()

    private val _friend = MutableStateFlow<Person>(Person())
    val friend = _friend.asStateFlow()
    fun allGroup(friendId: Long) {
        viewModelScope.launch {
            friendSettingRepository.loadAAllGroupByFriendId(friendId).collect {
                _allGroupList.emit(it)
            }
        }
    }

    fun loadFriend(friendId: Long) {
        viewModelScope.launch {
            friendSettingRepository.loadFriendByFriendId(friendId).collect {
                _friend.emit(it)
            }
        }
    }
}