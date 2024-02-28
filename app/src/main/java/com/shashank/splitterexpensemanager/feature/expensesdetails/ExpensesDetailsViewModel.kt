package com.shashank.splitterexpensemanager.feature.expensesdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.expensesdetails.repository.ExpensesDetailsRepository
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesDetailsViewModel @Inject constructor(
    private val expensesDetailsRepository: ExpensesDetailsRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<ExpenseWithCategoryAndPerson?>(null)
    val expenses = _expenses.asStateFlow()

    private val _oweOwed = MutableStateFlow<List<OweOrOwedWithPerson>>(listOf())
    val oweOwed = _oweOwed.asStateFlow()
    fun loadExpensesDetails(expensesId: Long) {
        viewModelScope.launch {
            expensesDetailsRepository.loadExpensesByExpensesId(expensesId).collect {
                _expenses.emit(it)
            }
        }
    }

    fun loadOweOwedByExpensesId(expensesId: Long) {
        viewModelScope.launch {
            expensesDetailsRepository.loadAllOweOrOwedByExpensesId(expensesId).collect {
                _oweOwed.emit(it)
            }
        }
    }
}