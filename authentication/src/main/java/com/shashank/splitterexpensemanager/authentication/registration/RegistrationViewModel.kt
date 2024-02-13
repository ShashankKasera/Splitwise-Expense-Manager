package com.shashank.splitterexpensemanager.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.google.firebase.auth.FirebaseAuth
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegistrationViewModel @Inject constructor(var registrationRepository: RegistrationRepository) :
    ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _networkState = MutableStateFlow<NetworkCallState>(NetworkCallState.Init)
    var networkState = _networkState.asStateFlow()

    private val _registrationUiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState())
    val registrationUiState = _registrationUiState.asStateFlow()

    private val _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()

    fun registration(email: String, password: String) {
        viewModelScope.launch {
            try {
                _networkState.emit(NetworkCallState.Loading)
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                _networkState.emit(NetworkCallState.Success)
                _registrationUiState.emit(
                    registrationUiState.value.copy(
                        user = result.user
                    )
                )
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
            }
        }
    }

    suspend fun insertPerson(person: PersonEntity) = viewModelScope.launch {
        registrationRepository.insertPerson(person)
    }

    suspend fun insertAllCategory(vararg category: Category) = viewModelScope.launch {
        registrationRepository.insertAllCategory(*category)
    }

    fun personLiveData(email: String) {
        viewModelScope.launch {
            registrationRepository.loadPerson(email).collect {
                _person.emit(it)
            }
        }
    }
}