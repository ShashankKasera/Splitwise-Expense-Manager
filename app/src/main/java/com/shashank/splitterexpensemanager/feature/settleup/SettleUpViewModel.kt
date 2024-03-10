package com.shashank.splitterexpensemanager.feature.settleup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.settleup.repository.SettleUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettleUpViewModel @Inject constructor(
    private val settleUpRepository: SettleUpRepository
) : ViewModel() {

    private val _settleUp = MutableStateFlow<List<Pair<Person, Double>>>(listOf())
    val settleUp = _settleUp.asStateFlow()
    fun settleUp(groupId: Long, personId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hashMap = HashMap<Person, Double>()

                val owedDeferred =
                    async { settleUpRepository.loadAllOwedByGroupId(groupId, personId) }

                val oweDeferred =
                    async { settleUpRepository.loadAllOweByGroupId(groupId, personId) }

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
                _settleUp.emit(hashMap.toList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}