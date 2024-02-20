package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val addGroupRepository: AddGroupRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    fun insertGroup(groupName: String, groupType: String) {
        viewModelScope.launch {
            addGroupRepository.insertGroup(Group(null, groupName, groupType, ""))
            val id = addGroupRepository.insertGroup(group)
            val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
            addGroupRepository.insertGroupMember(GroupMember(null, personId, id))
        }
    }
}