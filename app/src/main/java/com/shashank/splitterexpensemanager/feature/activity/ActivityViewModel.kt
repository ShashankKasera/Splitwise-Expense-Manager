package com.shashank.splitterexpensemanager.feature.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.activity.repository.ActivityRepository
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
) : ViewModel() {
    private val _allExpenses = MutableStateFlow<List<ExpenseWithCategoryAndPersonAndGroup>>(listOf())
    val allExpenses = _allExpenses.asStateFlow()

    private val _allRepay = MutableStateFlow<List<RepayWithPersonAndGroup>>(listOf())
    val allRepay = _allRepay.asStateFlow()

    fun allExpenses() {
        viewModelScope.launch {
            activityRepository.loadAllExpenses().collect {
                _allExpenses.emit(it)
            }
        }
    }

    fun allRepay() {
        viewModelScope.launch {
            activityRepository.loadAllRepay().collect {
                _allRepay.emit(it)
            }
        }
    }
}