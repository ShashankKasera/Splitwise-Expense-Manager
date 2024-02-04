package com.shashank.splitterexpensemanager.feature.addgroup.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addgroup.ui.model.GroupType
import com.shashank.splitterexpensemanager.feature.addgroup.data.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.ui.mapper.GroupListMapper
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    var repository: AddGroupRepository,
    var mapper: GroupListMapper
): ViewModel() {

    private val _groupType = MutableStateFlow<List<GroupType>>(listOf())
    var groupType = _groupType.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getGroupTypeList().collect{
                val list = mapper.map(it)
                _groupType.emit(list)
            }
        }
    }

    fun insertGroup(group: Group){
        val list = repository.insertGroup(group)
    }

}