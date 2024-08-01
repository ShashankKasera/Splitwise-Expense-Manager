package com.shashank.splitterexpensemanager.feature.createfriens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity

@HiltViewModel
class CreateFriendsViewModel @Inject constructor(
    var createFriendsRepository: CreateFriendsRepository,
) : ViewModel() {

    private val _person = MutableStateFlow<Person>(Person())
    val person = _person.asStateFlow()

    fun insertPerson(name: String, number: String, gender: String) {
        viewModelScope.launch {
            createFriendsRepository.insertPerson(
                PersonEntity(
                    null,
                    name,
                    String.EMPTY,
                    number,
                    gender
                )
            )
        }
    }

    fun loadPerson(personId: Long) {
        viewModelScope.launch {
            createFriendsRepository.loadPerson(personId).collect {
                _person.emit(it)
            }
        }
    }

    fun updatePerson(person: PersonEntity) {
        viewModelScope.launch {
            createFriendsRepository.updatePerson(person)
        }
    }
}