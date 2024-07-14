package com.shashank.splitterexpensemanager.feature.selectrepay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.groupmember.repository.GroupMemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRepayViewModel @Inject constructor(
    private val groupMemberRepository: GroupMemberRepository
) : ViewModel() {
    private val _allPerson = MutableStateFlow<List<Person>>(listOf())
    val allPerson = _allPerson.asStateFlow()
    fun allGroupMember(groupId: Long) {
        viewModelScope.launch {
            groupMemberRepository.loadAllGroupMemberWithGroupId(groupId).collect {
                _allPerson.emit(it)
            }
        }
    }
}