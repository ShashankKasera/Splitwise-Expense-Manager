package com.shashank.splitterexpensemanager.feature.addgroup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addgroup.data.AddGroupRepository
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    var addGroupRepository: AddGroupRepository,
) : ViewModel() {

    suspend fun insertGroup(group: Group) = viewModelScope.launch {
        addGroupRepository.insertGroup(group)
    }
}