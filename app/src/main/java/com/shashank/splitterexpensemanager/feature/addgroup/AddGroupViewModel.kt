package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val addGroupRepository: AddGroupRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val _group = MutableStateFlow<Group>(Group())
    val group = _group.asStateFlow()
    fun insertGroup(groupName: String, groupType: String) {
        viewModelScope.launch {
            val id = addGroupRepository.insertGroup(GroupEntity(null, groupName, groupType, ""))
            val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
            addGroupRepository.insertGroupMember(GroupMember(null, personId, id))
        }
    }

    fun updateGroup(group: GroupEntity) {
        viewModelScope.launch {
            addGroupRepository.updateGroup(group)
        }
    }

    fun loadGroup(groupId: Long) {
        viewModelScope.launch {
            addGroupRepository.loadGroup(groupId).collect {
                _group.emit(it)
            }
        }
    }
}