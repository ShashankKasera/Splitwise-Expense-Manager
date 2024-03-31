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
    private val _allGroup = MutableStateFlow(Pair<List<GroupAndOweOrOwed>, Double>(listOf(), -1.0))
    val allGroup = _allGroup.asStateFlow()

    private val _allGroupYouOwe =
        MutableStateFlow(Pair<List<GroupAndOweOrOwed>, Double>(listOf(), -1.0))
    val allGroupYouOwe = _allGroupYouOwe.asStateFlow()

    private val _allGroupWhoOweYou =
        MutableStateFlow(Pair<List<GroupAndOweOrOwed>, Double>(listOf(), -1.0))
    val allGroupWhoOweYou = _allGroupWhoOweYou.asStateFlow()

    private val _groupSize = MutableStateFlow(-1)
    val groupSize = _groupSize.asStateFlow()


    fun getAllGroup(personId: Long) {
        var youOverallOweOrOwed = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            groupRepository.loadAllGroup().collect {
                val groupAndOweOrOwed = mutableListOf<GroupAndOweOrOwed>()
                it.forEach {
                    try {
                        val hashMap = HashMap<Person, Double>()
                        val owedDeferred =
                            async { groupRepository.loadAllOwedByGroupId(it.id ?: -1, personId) }

                        val oweDeferred =
                            async { groupRepository.loadAllOweByGroupId(it.id ?: -1, personId) }

                        val owed = owedDeferred.await()
                        val owe = oweDeferred.await()


                        var oweOwed = 0.0
                        owed.forEach {
                            if (it.personOwe.id != it.personOwed.id) {
                                oweOwed += it.oweOrOwed.amount
                                hashMap[it.personOwe] =
                                    ((hashMap[it.personOwe]) ?: 0.0).plus(it.oweOrOwed.amount)
                            }
                        }
                        owe.forEach {
                            if (it.personOwe.id == personId) {
                                oweOwed -= it.oweOrOwed.amount
                                hashMap[it.personOwed] =
                                    ((hashMap[it.personOwed]) ?: 0.0).minus(it.oweOrOwed.amount)
                            }
                        }
                        youOverallOweOrOwed += oweOwed
                        groupAndOweOrOwed.add(GroupAndOweOrOwed(it, hashMap, oweOwed))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                _allGroup.emit(Pair(groupAndOweOrOwed, youOverallOweOrOwed))
            }
        }
    }

    fun getAllGroupYouOwe(personId: Long) {
        var youOverallOweOrOwed = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            groupRepository.loadAllGroup().collect {
                val groupAndOweOrOwed = mutableListOf<GroupAndOweOrOwed>()
                it.forEach {
                    try {
                        val hashMap = HashMap<Person, Double>()
                        val owedDeferred =
                            async { groupRepository.loadAllOwedByGroupId(it.id ?: -1, personId) }

                        val oweDeferred =
                            async { groupRepository.loadAllOweByGroupId(it.id ?: -1, personId) }

                        val owed = owedDeferred.await()
                        val owe = oweDeferred.await()


                        var oweOwed = 0.0
                        owed.forEach {
                            if (it.personOwe.id != it.personOwed.id) {
                                oweOwed += it.oweOrOwed.amount
                                hashMap[it.personOwe] =
                                    ((hashMap[it.personOwe]) ?: 0.0).plus(it.oweOrOwed.amount)
                            }
                        }
                        owe.forEach {
                            if (it.personOwe.id == personId) {
                                oweOwed -= it.oweOrOwed.amount
                                hashMap[it.personOwed] =
                                    ((hashMap[it.personOwed]) ?: 0.0).minus(it.oweOrOwed.amount)
                            }
                        }

                        val flag = oweOwed < 0
                        if (flag) {
                            youOverallOweOrOwed += oweOwed
                            groupAndOweOrOwed.add(GroupAndOweOrOwed(it, hashMap, oweOwed))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                _allGroupYouOwe.emit(Pair(groupAndOweOrOwed, youOverallOweOrOwed))
            }
        }
    }

    fun getAllGroupWhoOweYou(personId: Long) {
        var youOverallOweOrOwed = 0.0
        viewModelScope.launch(Dispatchers.IO) {
            groupRepository.loadAllGroup().collect {
                val groupAndOweOrOwed = mutableListOf<GroupAndOweOrOwed>()
                it.forEach {
                    try {
                        val hashMap = HashMap<Person, Double>()
                        val owedDeferred =
                            async { groupRepository.loadAllOwedByGroupId(it.id ?: -1, personId) }

                        val oweDeferred =
                            async { groupRepository.loadAllOweByGroupId(it.id ?: -1, personId) }

                        val owed = owedDeferred.await()
                        val owe = oweDeferred.await()


                        var oweOwed = 0.0
                        owed.forEach {
                            if (it.personOwe.id != it.personOwed.id) {
                                oweOwed += it.oweOrOwed.amount
                                hashMap[it.personOwe] =
                                    ((hashMap[it.personOwe]) ?: 0.0).plus(it.oweOrOwed.amount)
                            }
                        }
                        owe.forEach {
                            if (it.personOwe.id == personId) {
                                oweOwed -= it.oweOrOwed.amount
                                hashMap[it.personOwed] =
                                    ((hashMap[it.personOwed]) ?: 0.0).minus(it.oweOrOwed.amount)
                            }
                        }

                        val flag = oweOwed > 0
                        if (flag) {
                            youOverallOweOrOwed += oweOwed
                            groupAndOweOrOwed.add(GroupAndOweOrOwed(it, hashMap, oweOwed))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                _allGroupWhoOweYou.emit(Pair(groupAndOweOrOwed, youOverallOweOrOwed))
            }
        }
    }

    fun getGroupSize() {
        viewModelScope.launch {
            groupRepository.loadAllGroup().collect {
                _groupSize.emit(it.size)
            }
        }
    }
}