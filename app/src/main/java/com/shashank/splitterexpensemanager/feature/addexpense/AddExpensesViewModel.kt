package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.localdb.room.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(var expensesRepository: ExpensesRepository) :
    ViewModel() {

    var expensesLiveData = expensesRepository.loadAllExpenses()

    suspend fun insertExpenses(expenses: Expenses) = viewModelScope.launch {
        expensesRepository.insertExpenses(expenses)
    }

    suspend fun insertAllExpenses(vararg expenses: Expenses) = viewModelScope.launch {
        expensesRepository.insertAllExpenses(*expenses)
    }
}