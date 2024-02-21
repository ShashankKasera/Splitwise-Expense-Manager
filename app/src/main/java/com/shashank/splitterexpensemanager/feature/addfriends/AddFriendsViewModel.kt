package com.shashank.splitterexpensemanager.feature.addfriends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addfriends.repository.AddFriendsRepository
import com.shashank.splitterexpensemanager.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.GroupMember as GroupMemberEntity
import com.shashank.splitterexpensemanager.authentication.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    var addFriendsRepository: AddFriendsRepository,
) : ViewModel() {

    private val _allPersonExcept = MutableStateFlow<List<Person>>(listOf())
    val allPersonExcept = _allPersonExcept.asStateFlow()

    private val _allGroupMember = MutableStateFlow<List<GroupMember>>(listOf())
    val allGroupMember = _allGroupMember.asStateFlow()
    fun getAllGroupMember() {
        viewModelScope.launch {
            addFriendsRepository.loadAllGroupMember().collect {
                _allGroupMember.emit(it)
            }
        }
    }

    fun insertGroupMember(personId: Long, groupId: Long) {
        viewModelScope.launch {
            addFriendsRepository.insertGroupMember(GroupMemberEntity(null, personId, groupId))
        }
    }

    fun deleteGroupMember(personId: Long) = viewModelScope.launch {
        addFriendsRepository.deleteGroupMember(personId)
    }

    fun getAllPersonExcept(personId: Long) {
        viewModelScope.launch {
            addFriendsRepository.loadPersonExcept(personId).collect {
                _allPersonExcept.emit(it)
            }
        }
    }
}