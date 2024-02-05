package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.lifecycle.ViewModel
import com.shashank.splitterexpensemanager.feature.groupdetails.data.GroupDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class GroupDetailViewModel @Inject constructor(var groupDetailsRepository: GroupDetailsRepository) :
    ViewModel() {
    fun groupLiveData(groupId: Long) = groupDetailsRepository.loadGroup(groupId)
}