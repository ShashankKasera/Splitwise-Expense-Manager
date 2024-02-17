package com.shashank.splitterexpensemanager.feature.createfriens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepository
import com.shashank.splitterexpensemanager.localdb.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CreateFriendsViewModel @Inject constructor(
    var createFriendsRepository: CreateFriendsRepository,
) : ViewModel() {

    suspend fun insertPerson(person: Person) = viewModelScope.launch {
        createFriendsRepository.insertPerson(person)
    }
}