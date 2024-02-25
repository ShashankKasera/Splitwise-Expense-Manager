package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.model.GroupDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(var groupDetailsRepository: GroupDetailsRepository) :
    ViewModel() {

    private val _groupDetails = MutableStateFlow<GroupDetails>(GroupDetails())
    val groupDetails = _groupDetails.asStateFlow()
    fun groupDetails(groupId: Long, personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hashMap = HashMap<Person, Double>()
                val groupDeferred = async { groupDetailsRepository.loadGroup(groupId) }
                val groupMemberDeferred =
                    async { groupDetailsRepository.loadAllGroupMemberWithGroupId(groupId) }
                val expensesDeferred = async { groupDetailsRepository.loadGroupExpenses(groupId) }
                val oweDeferred =
                    async { groupDetailsRepository.loadAllOweByGroupId(groupId, personId) }
                val owedDeferred =
                    async { groupDetailsRepository.loadAllOwedByGroupId(groupId, personId) }

                val owe = oweDeferred.await()
                val owed = owedDeferred.await()
                owe.forEach {
                    if (it.personOwe.id != it.personOwed.id) {
                        hashMap[it.personOwed] =
                            ((hashMap[it.personOwed]) ?: 0.0).plus(it.oweOrOwed.amount)
                    }
                }

                owed.forEach {
                    if (it.personOwed.id == personId) {
                        hashMap[it.personOwe] =
                            ((hashMap[it.personOwe]) ?: 0.0).minus(it.oweOrOwed.amount)
                    }
                }
                val data = GroupDetails(
                    group = groupDeferred.await(),
                    groupMember = groupMemberDeferred.await(),
                    expenses = expensesDeferred.await(),
                    hashMap = hashMap
                )

                _groupDetails.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}