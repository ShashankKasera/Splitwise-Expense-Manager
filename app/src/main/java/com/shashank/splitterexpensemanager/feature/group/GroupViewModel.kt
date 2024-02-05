package com.shashank.splitterexpensemanager.feature.group

import androidx.lifecycle.ViewModel
import com.shashank.splitterexpensemanager.feature.group.data.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(var groupRepository: GroupRepository) :
    ViewModel() {
    var allGroupLiveData = groupRepository.loadAllGroup()
}