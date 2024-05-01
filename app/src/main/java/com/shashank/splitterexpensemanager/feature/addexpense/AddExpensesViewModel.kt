package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.addexpense.repository.AddExpensesRepository
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val addExpensesRepository: AddExpensesRepository
) : ViewModel() {

    val allGroupMemberList = mutableListOf<Person>()
    val allOweOrOwedList = mutableListOf<OweOrOwed>()

    private val _groupMemberFetchedForInsertOweOwed = MutableStateFlow<Boolean>(false)
    val groupMemberFetchedForInsertOweOwed = _groupMemberFetchedForInsertOweOwed.asStateFlow()

    private val _oweOrOwedFetchedForUpdate = MutableStateFlow<Boolean>(false)
    val oweOrOwedFetchedForUpdate = _oweOrOwedFetchedForUpdate.asStateFlow()

    private val _whoPayPerson = MutableStateFlow<Person?>(null)
    val whoPayPerson = _whoPayPerson.asStateFlow()

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    private val _expensesInsertSuccessfully = MutableStateFlow<Boolean>(false)
    val expensesInsertSuccessfully = _expensesInsertSuccessfully.asStateFlow()

    private val _expensesUpdateSuccessfully = MutableStateFlow<Boolean>(false)
    val expensesUpdateSuccessfully = _expensesUpdateSuccessfully.asStateFlow()

    private val _expensesDetails = MutableStateFlow<ExpenseWithCategoryAndPerson?>(null)
    val expensesDetails = _expensesDetails.asStateFlow()
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
                _whoPayPerson.emit(it)
            }
        }
    }

    fun allGroupMember(groupId: Long) {
        viewModelScope.launch {
            addExpensesRepository.loadAllGroupMemberWithGroupId(groupId).collect {
                allGroupMemberList.clear()
                allGroupMemberList.addAll(it)
                _groupMemberFetchedForInsertOweOwed.emit(true)
            }
        }
    }

    fun allOweOrOwed(expensesId: Long) {
        viewModelScope.launch {
            allOweOrOwedList.clear()
            allOweOrOwedList.addAll(addExpensesRepository.loadAllOweOrOwedByExpensesId(expensesId))
            _oweOrOwedFetchedForUpdate.emit(true)
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
        val expensesId = addExpensesRepository.insertExpenses(
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
        allGroupMemberList.forEach { member ->
            val owedId = member.id ?: 0
            addExpensesRepository.insertOweOrOwed(
                OweOrOwedEntity(
                    null,
                    expensesId,
                    personId,
                    owedId,
                    groupId,
                    splitAmount
                )
            )
        }
        _expensesInsertSuccessfully.emit(true)
    }

    suspend fun updateExpenses(
        expensesId: Long,
        personId: Long,
        groupId: Long,
        categoryId: Long,
        amount: Double,
        name: String,
        date: String,
        time: String,
        description: String,
    ) {
        viewModelScope.launch {
            if (allOweOrOwedList.size > 0) {
                val numberOfMember = allOweOrOwedList.size
                val splitAmount = (amount / numberOfMember)
                addExpensesRepository.updateExpenses(
                    Expenses(
                        expensesId,
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

                allOweOrOwedList.forEach {
                    addExpensesRepository.updateOweOrOwed(
                        OweOrOwedEntity(
                            id = it.id,
                            expensesId = it.expensesId,
                            personOweId = personId,
                            personOwedId = it.personOwedId,
                            groupId = it.groupId,
                            amount = splitAmount
                        )
                    )
                }
                _expensesUpdateSuccessfully.emit(true)
            }
        }
    }

    fun loadExpensesDetails(expensesId: Long) {
        viewModelScope.launch {
            addExpensesRepository.loadExpensesByExpensesId(expensesId).collect {
                _expensesDetails.emit(it)
            }
        }
    }
}