package com.example.splitwiseexpensemanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localdb.model.Person
import com.example.localdb.room.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(var personRepository: PersonRepository) : ViewModel() {
    var postLiveDate = personRepository.loadAllPerson()
    fun insertAllPerson(vararg person: Person) = viewModelScope.launch {
        personRepository.insertAllPerson(*person)
    }
}