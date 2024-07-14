package com.shashank.splitterexpensemanager.feature.groupsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.groupsettings.repository.GroupSettingsRepository
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.model.GroupSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupSettingsViewModel @Inject constructor(var groupSettingRepository: GroupSettingsRepository) :
    ViewModel() {

    private val _groupSetting = MutableStateFlow<GroupSettings>(GroupSettings())
    val groupSetting = _groupSetting.asStateFlow()
    fun groupSetting(groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val groupDeferred = async { groupSettingRepository.loadGroup(groupId) }
                val groupMemberDeferred =
                    async { groupSettingRepository.loadAllGroupMemberWithGroupId(groupId) }
                val oweOwedDeferred =
                    async { groupSettingRepository.loadAllOweOwedByGroupId(groupId) }

                val groupMembersHashMap: HashMap<Person, Double> =
                    HashMap(groupMemberDeferred.await().associate { it to 0.0 })

                oweOwedDeferred.await().forEach {
                    groupMembersHashMap[it.personOwed] =
                        ((groupMembersHashMap[it.personOwed]) ?: 0.0).plus(it.oweOrOwed.amount)
                    groupMembersHashMap[it.personOwe] =
                        ((groupMembersHashMap[it.personOwe]) ?: 0.0).minus(it.oweOrOwed.amount)
                }
                val data = GroupSettings(
                    group = groupDeferred.await(),
                    groupMembersHashMap = groupMembersHashMap
                )
                _groupSetting.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteGroup(group: Group) {
        viewModelScope.launch {
            groupSettingRepository.deleteGroup(group)
        }
    }
}