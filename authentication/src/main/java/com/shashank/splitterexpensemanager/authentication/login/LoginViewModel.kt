package com.shashank.splitterexpensemanager.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.shashank.splitterexpensemanager.authentication.login.repository.LoginRepository
import com.shashank.splitterexpensemanager.authentication.model.Person
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
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPref: SharedPref
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _networkState = MutableStateFlow<NetworkCallState>(NetworkCallState.Init)
    var networkState = _networkState.asStateFlow()
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _networkState.emit(NetworkCallState.Loading)
                auth.signInWithEmailAndPassword(email, password).await()

                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    getUser(user)
                }

                _networkState.emit(NetworkCallState.Success)
                _loginUiState.emit(
                    loginUiState.value.copy()
                )
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
            }
        }
    }

    fun loadPersonByEmail(email: String) {
        viewModelScope.launch {
            loginRepository.loadPersonByEmail(email).collect {
                if (it.id != null) {
                    sharedPref.setValue(PERSON_ID, it.id)
                }
            }
        }
    }

    fun getUser(user: FirebaseUser) {
        val databaseReference = FirebaseDatabase.getInstance().getReference(PERSON).child(user.uid)

        viewModelScope.launch {
            try {
                val snapshot = databaseReference.get().await()
                val person = snapshot.getValue(Person::class.java)
                val userName = person?.name ?: String.EMPTY
                val userEmailAddress = person?.emailId ?: String.EMPTY
                val userGender = person?.gender ?: String.EMPTY
                loginRepository.insertPerson(
                    PersonEntity(
                        null,
                        userName,
                        userEmailAddress,
                        String.EMPTY,
                        userGender
                    )
                )
                loadPersonByEmail(userEmailAddress)
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
            }
        }
    }

    suspend fun insertAllCategory(vararg category: Category) = viewModelScope.launch {
        loginRepository.insertAllCategory(*category)
    }
}