package com.shashank.splitterexpensemanager.feature.groupmember

import androidx.lifecycle.ViewModel
import com.shashank.splitterexpensemanager.feature.groupmember.data.GroupMemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class GroupMemberViewModel @Inject constructor(var groupMemberRepository: GroupMemberRepository) :
    ViewModel() {
    fun allGroupLiveData(groupId: Long) = groupMemberRepository.loadAllGroupMemberWithGroupId(groupId)
}