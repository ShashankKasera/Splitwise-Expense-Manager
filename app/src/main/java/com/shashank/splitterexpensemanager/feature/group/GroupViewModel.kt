package com.shashank.splitterexpensemanager.feature.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepository
import com.shashank.splitterexpensemanager.model.GroupAndOweOrOwed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {
    private val _allGroup = MutableStateFlow<List<GroupAndOweOrOwed>>(listOf())
    val allGroup = _allGroup.asStateFlow()


    fun getAllGroup(personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            groupRepository.loadAllGroup().collect {
                val groupAndOweOrOwed = mutableListOf<GroupAndOweOrOwed>()
                it.forEach {
                    try {
                        val hashMap = HashMap<Person, Double>()
                        val oweDeferred =
                            async { groupRepository.loadAllOweByGroupId(it.id ?: -1, personId) }
                        val owedDeferred =
                            async { groupRepository.loadAllOwedByGroupId(it.id ?: -1, personId) }

                        val owe = oweDeferred.await()
                        val owed = owedDeferred.await()
                        var oweOwed = 0.0
                        owe.forEach {
                            if (it.personOwe.id != it.personOwed.id) {
                                oweOwed += it.oweOrOwed.amount
                                hashMap[it.personOwed] =
                                    ((hashMap[it.personOwed]) ?: 0.0).plus(it.oweOrOwed.amount)
                            }
                        }

                        owed.forEach {
                            if (it.personOwed.id == personId) {
                                oweOwed -= it.oweOrOwed.amount
                                hashMap[it.personOwe] =
                                    ((hashMap[it.personOwe]) ?: 0.0).minus(it.oweOrOwed.amount)
                            }
                        }

                        groupAndOweOrOwed.add(GroupAndOweOrOwed(it, hashMap, oweOwed))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                _allGroup.emit(groupAndOweOrOwed)
            }
        }
    }
}