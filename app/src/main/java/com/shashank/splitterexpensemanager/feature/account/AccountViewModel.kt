package com.shashank.splitterexpensemanager.feature.account


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.account.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
) : ViewModel() {
    private val _person = MutableStateFlow<Person>(Person())
    val person = _person.asStateFlow()

    fun loadPerson(personId: Long) {
        viewModelScope.launch {
            accountRepository.loadPersonById(personId).collect {
                _person.emit(it)
            }
        }
    }
}