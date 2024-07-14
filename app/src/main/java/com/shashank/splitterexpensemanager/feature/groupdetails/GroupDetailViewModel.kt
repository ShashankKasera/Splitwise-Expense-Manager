package com.shashank.splitterexpensemanager.feature.groupdetails

import android.util.Log
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

    var groupId: Long = -1
    var personId: Long = -1
    fun groupDetails(groupId: Long, personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hashMap = HashMap<Person, Double>()
                val groupDeferred = async { groupDetailsRepository.loadGroup(groupId) }
                val groupMemberDeferred =
                    async { groupDetailsRepository.loadAllGroupMemberWithGroupId(groupId) }
                val expensesDeferred = async { groupDetailsRepository.loadGroupExpenses(groupId) }
                val repayDeferred = async { groupDetailsRepository.loadGroupRepay(groupId) }
                val owedDeferred =
                    async { groupDetailsRepository.loadAllOwedByGroupId(groupId, personId) }

                val oweDeferred =
                    async { groupDetailsRepository.loadAllOweByGroupId(groupId, personId) }

                val owed = owedDeferred.await()
                val owe = oweDeferred.await()

                owed.forEach {
                    if (it.personOwe.id != it.personOwed.id) {
                        hashMap[it.personOwe] =
                            ((hashMap[it.personOwe]) ?: 0.0).plus(it.oweOrOwed.amount)
                    }
                }
                owe.forEach {
                    if (it.personOwe.id == personId) {
                        hashMap[it.personOwed] =
                            ((hashMap[it.personOwed]) ?: 0.0).minus(it.oweOrOwed.amount)
                    }
                }


                Log.i("jhjh", "groupDetails: $hashMap")
                val data = GroupDetails(
                    group = groupDeferred.await(),
                    groupMember = groupMemberDeferred.await(),
                    expenses = ((expensesDeferred.await()) ?: emptyList()),
                    repay = ((repayDeferred.await()) ?: emptyList()),
                    hashMap = hashMap
                )

                _groupDetails.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}