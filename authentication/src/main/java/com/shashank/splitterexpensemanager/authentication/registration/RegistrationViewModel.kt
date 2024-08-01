package com.shashank.splitterexpensemanager.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.core.PERSON
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val _networkState = MutableStateFlow<NetworkCallState>(NetworkCallState.Init)
    var networkState = _networkState.asStateFlow()

    fun registration(name: String, email: String, password: String, gender: String) {
        viewModelScope.launch {
            try {
                _networkState.emit(NetworkCallState.Loading)
                auth.createUserWithEmailAndPassword(email, password).await()
                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    setUser(user, name, email, gender)
                }
                registrationRepository.insertPerson(PersonEntity(null, name, email, String.EMPTY, gender))
                loadPersonByEmail(email)
                _networkState.emit(NetworkCallState.Success)
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
            }
        }
    }

    private suspend fun setUser(user: FirebaseUser, name: String, email: String, gender: String) {
        val personKey = databaseReference.child(PERSON).child(user.uid)

        if (personKey != null) {
            try {
                personKey.setValue(Person(null, name, email, String.EMPTY, gender)).await()
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
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

    suspend fun insertAllCategory(vararg category: Category) = viewModelScope.launch {
        registrationRepository.insertAllCategory(*category)
    }
}