package com.shashank.splitterexpensemanager.feature.balances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.balances.repository.BalancesRepository
import com.shashank.splitterexpensemanager.model.Balances
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalancesViewModel @Inject constructor(
    private val balancesRepository: BalancesRepository,
) : ViewModel() {
    private val _allBalance = MutableStateFlow<List<Balances>>(listOf())
    val allBalance = _allBalance.asStateFlow()
    fun getBalances(groupId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val balancesList = mutableListOf<Balances>()
                val groupMemberDeferred = async { balancesRepository.loadAllGroupMemberWithGroupId(groupId) }

                groupMemberDeferred.await().forEach { person ->
                    val oweOwedDeferred = async {
                        balancesRepository.loadAllOweOwedByGroupIdAndPersonId(groupId, person.id ?: -1)
                    }

                    val groupMembersHashMap: HashMap<Person, Double> = HashMap()
                    var amount = 0.0

                    oweOwedDeferred.await().forEach { oweOwed ->
                        when {
                            oweOwed.personOwed == person -> {
                                amount += oweOwed.oweOrOwed.amount
                                groupMembersHashMap[oweOwed.personOwe] =
                                    ((groupMembersHashMap[oweOwed.personOwe]) ?: 0.0).plus(oweOwed.oweOrOwed.amount)
                            }
                            oweOwed.personOwe == person -> {
                                amount -= oweOwed.oweOrOwed.amount
                                groupMembersHashMap[oweOwed.personOwed] =
                                    ((groupMembersHashMap[oweOwed.personOwed]) ?: 0.0).minus(oweOwed.oweOrOwed.amount)
                            }
                        }
                    }

                    val oweOwedList = groupMembersHashMap.toList().filter { it.second != 0.0 }.toMutableList()
                    balancesList.add(Balances(person, oweOwedList, amount))
                }
                _allBalance.emit(balancesList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}