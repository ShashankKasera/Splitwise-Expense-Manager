package com.shashank.splitterexpensemanager.feature.groupdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(var groupDetailsRepository: GroupDetailsRepository) :
    ViewModel() {

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    private var _oweOrOwed = MutableStateFlow<String?>("")
    val oweOrOwed = _oweOrOwed.asStateFlow()

    private var _amount = MutableStateFlow<Double?>(0.0)
    var amount = _amount.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseWithCategoryAndPerson?>>(listOf())
    val expenses = _expenses.asStateFlow()

    fun getGroup(groupId: Long) {
        viewModelScope.launch {
            groupDetailsRepository.loadGroup(groupId).collect {
                _group.emit(it)
            }
        }
    }

    fun loadAllExpensesLiveData(personId: Long, groupId: Long) {
        viewModelScope.launch {
            groupDetailsRepository.loadGroupExpenses(groupId).collect {
                var amount=0.0
                for (expense in it) {
                    var fullAmount=0.0
                    if(expense.expense.personId==personId)
                        fullAmount= expense.expense.amount
                    val splitAmount = expense.expense.splitAmount
                    amount+=fullAmount-splitAmount
                }

                _amount.emit(amount)
                if(amount >0){
                    _oweOrOwed.emit("You Are Owed Rs ${amount.formatNumber(2)} Overall")
                }else{
                    _oweOrOwed.emit("You Are Owe Rs ${(-(amount)).formatNumber(2)} Overall")
                }

                _expenses.emit(it)
            }
        }
    }

    fun loadAllOweByGroupId(groupId: Long,personId:Long){
        viewModelScope.launch {
            groupDetailsRepository.loadAllOweByGroupId(groupId,personId).collect{
                _owe.emit(it)
            }
        }
    }

    fun loadAllOwedByGroupId(groupId: Long,personId:Long){
        viewModelScope.launch {
            groupDetailsRepository.loadAllOwedByGroupId(groupId,personId).collect{
                _owed.emit(it)
            }
        }
    }
}