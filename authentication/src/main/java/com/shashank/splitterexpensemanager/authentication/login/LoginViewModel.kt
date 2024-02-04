package com.shashank.splitterexpensemanager.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.core.network.NetworkCallState
import com.google.firebase.auth.FirebaseAuth
import com.shashank.splitterexpensemanager.authentication.login.repository.LoginRepository
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(var loginRepository: LoginRepository) : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _networkState = MutableStateFlow<NetworkCallState>(NetworkCallState.Init)
    var networkState = _networkState.asStateFlow()
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _networkState.emit(NetworkCallState.Loading)
                val result = auth.signInWithEmailAndPassword(email, password)
                    .await()
                _networkState.emit(NetworkCallState.Success)
                _loginUiState.emit(
                    loginUiState.value.copy(
                        user = result.user
                    )
                )
            } catch (e: Exception) {
                _networkState.emit(NetworkCallState.Error(e.message.toString()))
            }
        }
    }

    suspend fun insertPerson(person: Person) = viewModelScope.launch {
        loginRepository.insertPerson(person)
    }
    suspend fun insertAllCategory(vararg category: Category) = viewModelScope.launch {
        loginRepository.insertAllCategory(*category)
    }
}