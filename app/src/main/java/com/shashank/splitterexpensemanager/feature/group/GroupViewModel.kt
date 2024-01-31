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
    var groupLiveData = groupRepository.loadAllGroup()

    suspend fun insertGroup(group: Group) = viewModelScope.launch {
        groupRepository.insertGroup(group)
    }

    suspend fun insertAllGroup(vararg group: Group) = viewModelScope.launch {
        groupRepository.insertAllGroup(*group)
    }
}