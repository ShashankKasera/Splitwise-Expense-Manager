package com.shashank.splitterexpensemanager.feature.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.room.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(var groupRepository: GroupRepository) :
    ViewModel() {
    var allGroupLiveData = groupRepository.loadAllGroup()

    suspend fun insertGroup(group: Group) = viewModelScope.launch {
        groupRepository.insertGroup(group)
    }

     fun groupLiveData(groupId: Long) =  groupRepository.loadGroup(groupId)

    suspend fun insertAllGroup(vararg group: Group) = viewModelScope.launch {
        groupRepository.insertAllGroup(*group)
    }
}