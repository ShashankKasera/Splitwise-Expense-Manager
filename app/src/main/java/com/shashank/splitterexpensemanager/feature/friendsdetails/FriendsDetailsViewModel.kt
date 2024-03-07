package com.shashank.splitterexpensemanager.feature.friendsdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.friendsdetails.repository.FriendsDetailsRepository
import com.shashank.splitterexpensemanager.model.Friends
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsDetailsViewModel @Inject constructor(
    private val friendsDetailsRepository:FriendsDetailsRepository
): ViewModel() {

    private val _allFriends = MutableStateFlow<Friends>(Friends())
    val allFriends = _allFriends.asStateFlow()
    fun loadAllFriends(personId: Long,personOwedId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                val personDeferred = async { friendsDetailsRepository.loadPerson() }
                val oweDeferred = async { friendsDetailsRepository.loadAllOweByOweIdAndOwedId(personId,personOwedId) }
                val owedDeferred = async { friendsDetailsRepository.loadAllOwedByOweIdAndOwedId(personId,personOwedId) }

                val allFriendsWithOweOwedHashmap: HashMap<Person, Double> = hashMapOf()

                val owe = oweDeferred.await()
                val owed = owedDeferred.await()
                var person = Person()
                var amount = 0.0
                owe.forEach {
                    if (it.personOwe.id != it.personOwed.id) {
                        amount += it.oweOrOwed.amount
                        allFriendsWithOweOwedHashmap[it.personOwed] =
                            ((allFriendsWithOweOwedHashmap[it.personOwed]) ?: 0.0).plus(it.oweOrOwed.amount)
                    } else {
                        person = it.personOwe
                    }
                }

                owed.forEach {
                    if (it.personOwed.id == personId) {
                        amount -= it.oweOrOwed.amount
                        person = it.personOwed
                        allFriendsWithOweOwedHashmap[it.personOwe] =
                            ((allFriendsWithOweOwedHashmap[it.personOwe]) ?: 0.0).minus(it.oweOrOwed.amount)
                    }
                }

                Log.i("ejgwk", "loadAllFriends: ${owe}        ${owed}  ")
                allFriendsWithOweOwedHashmap.remove(person)
                val data = Friends(
                    person = person,
                    friendsHashMap = allFriendsWithOweOwedHashmap,
                    overallOweOrOwed = amount
                )
                _allFriends.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}