package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val addGroupRepository: AddGroupRepository,
) : ViewModel() {
    fun insertGroup(groupName: String, groupType: String) {
        viewModelScope.launch {
            addGroupRepository.insertGroup(Group(null, groupName, groupType, ""))
        }
    }
}