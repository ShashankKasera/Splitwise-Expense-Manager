package com.shashank.splitterexpensemanager.feature.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepository
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) :
    ViewModel() {
    private val _allGroup = MutableStateFlow<List<Group>>(listOf())
    val allGroup = _allGroup.asStateFlow()

    fun getAllGroup() {
        viewModelScope.launch {
            groupRepository.loadAllGroup().collect {
                _allGroup.emit(it)
            }
        }
    }
}