package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(var groupDetailsRepository: GroupDetailsRepository) :
    ViewModel() {

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseWithCategoryAndPerson?>>(listOf())
    val expenses = _expenses.asStateFlow()

    fun getGroup(groupId: Long) {
        viewModelScope.launch {
            groupDetailsRepository.loadGroup(groupId).collect {
                _group.emit(it)
            }
        }
    }

    fun loadAllExpensesLiveData(groupId: Long) {
        viewModelScope.launch {
            groupDetailsRepository.loadGroupExpenses(groupId).collect {
                _expenses.emit(it)
            }
        }
    }
}