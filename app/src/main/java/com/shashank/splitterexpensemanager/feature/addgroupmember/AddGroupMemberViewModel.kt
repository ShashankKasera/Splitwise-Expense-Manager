package com.shashank.splitterexpensemanager.feature.addgroupmember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.repository.GroupMemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddGroupMemberViewModel @Inject constructor(var groupMemberRepository: GroupMemberRepository) :
    ViewModel() {
    var groupMemberLiveData = groupMemberRepository.loadAllGroupMember()

    suspend fun insertGroupMember(groupMember: GroupMember) = viewModelScope.launch {
        groupMemberRepository.insertGroupMember(groupMember)
    }

    suspend fun insertAllGroupMember(vararg groupMember: GroupMember) = viewModelScope.launch {
        groupMemberRepository.insertAllGroupMember(*groupMember)
    }
}