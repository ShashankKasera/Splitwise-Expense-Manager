package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.addexpense.repository.AddExpensesRepository
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val addExpensesRepository: AddExpensesRepository
) : ViewModel() {

    val allPerson = mutableListOf<Person>()

    private val _personFeched = MutableStateFlow<Boolean>(false)
    val personFeched = _personFeched.asStateFlow()

    private val _person = MutableStateFlow<Person?>(null)
    val person = _person.asStateFlow()

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()
    fun getGroup(groupId: Long) {
        viewModelScope.launch {
            addExpensesRepository.loadGroup(groupId).collect {
                _group.emit(it)
            }
        }
    }

    fun getPerson(personId: Long) {
        viewModelScope.launch {
            addExpensesRepository.loadPerson(personId).collect {
                _person.emit(it)
            }
        }
    }

    fun allGroupMember(groupId: Long) {
        viewModelScope.launch {
            addExpensesRepository.loadAllGroupMemberWithGroupId(groupId).collect {
                allPerson.addAll(it)
                _personFeched.emit(true)
            }
        }
    }

    suspend fun insertExpenses(
        personId: Long,
        groupId: Long,
        categoryId: Long,
        amount: Double,
        splitAmount: Double,
        name: String,
        date: String,
        time: String,
        description: String,
    ) = viewModelScope.launch {
        addExpensesRepository.insertExpenses(
            Expenses(
                null,
                personId,
                groupId,
                categoryId,
                amount,
                splitAmount,
                name,
                date,
                time,
                description
            )
        )
        allPerson.forEach { member ->
            val owedId = member.id ?: 0
            addExpensesRepository.insertOweOrOwed(
                OweOrOwed(
                    null,
                    personId,
                    owedId,
                    groupId,
                    splitAmount
                )
            )
        }
    }
}