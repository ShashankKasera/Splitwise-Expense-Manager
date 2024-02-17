package com.shashank.splitterexpensemanager.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.core.PERSON
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegistrationViewModel @Inject constructor(var registrationRepository: RegistrationRepository) :
    ViewModel() {
    @Inject
    lateinit var sharedPref: SharedPref
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference
    private val _networkState = MutableStateFlow<NetworkCallState>(NetworkCallState.Init)
    var networkState = _networkState.asStateFlow()

    fun registration(name: String, email: String, password: String) {
        databaseReference = FirebaseDatabase.getInstance().reference
        viewModelScope.launch {
            try {
                _networkState.emit(NetworkCallState.Loading)
                auth.createUserWithEmailAndPassword(email, password).await()
                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    setUser(user, name, email)
                }
                registrationRepository.insertPerson(PersonEntity(null, name, email, ""))
                loadPersonByEmail(email)
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

    private suspend fun setUser(user: FirebaseUser, name: String, email: String) {
        val personKey = databaseReference.child(PERSON).child(user.uid)

        if (personKey != null) {
            try {
                personKey.setValue(Person(null, name, email, "")).await()
            } catch (e: Exception) {
                Log.i("RegistrationViewModel", "setUser: ${e.message}")
            }
        }
    }

    fun loadPersonByEmail(email: String) {
        viewModelScope.launch {
            registrationRepository.loadPersonByEmail(email).collect {
                if (it.id != null) {
                    sharedPref.setValue(PERSON_ID, it.id)
                }
            }
        }
    }
}