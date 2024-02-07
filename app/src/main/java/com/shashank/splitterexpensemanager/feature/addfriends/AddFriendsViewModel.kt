package com.shashank.splitterexpensemanager.feature.addfriends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addfriends.data.AddFriendsRepository
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    var addFriendsRepository: AddFriendsRepository,
) : ViewModel() {
    var allPersonLiveData = addFriendsRepository.loadAllPerson()
    var allGroupMemberLiveData = addFriendsRepository.loadAllGroupMember()

    suspend fun insertGroupMember(groupMember: GroupMember) = viewModelScope.launch {
        addFriendsRepository.insertGroupMember(groupMember)
    }

    suspend fun deleteGroupMember(PersonId: Long) = viewModelScope.launch {
        addFriendsRepository.deleteGroupMember(PersonId)
    }
}