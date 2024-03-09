package com.shashank.splitterexpensemanager.feature.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.activity.repository.ActivityRepository
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
) : ViewModel() {
    private val _allActivity = MutableStateFlow<List<ExpenseWithCategoryAndPersonAndGroup>>(listOf())
    val allActivity = _allActivity.asStateFlow()
    fun allActivity() {
        viewModelScope.launch {
            activityRepository.loadAllExpenses().collect {
                _allActivity.emit(it)
            }
        }
    }
}