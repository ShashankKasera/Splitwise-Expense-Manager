package com.shashank.splitterexpensemanager.feature.repaydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.repaydetails.repository.RepayDetailsRepository
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepayDetailsViewModel @Inject constructor(
    private val repayDetailsRepository: RepayDetailsRepository
) : ViewModel() {
    private val _repay = MutableStateFlow<RepayWithPerson?>(null)
    val repay = _repay.asStateFlow()

    fun deleteRepay(repayId: Long) = viewModelScope.launch {
        repayDetailsRepository.deleteRepay(repayId)
    }

    fun loadRepayDetails(repayId: Long) {
        viewModelScope.launch {
            repayDetailsRepository.loadRepayByRepayId(repayId).collect {
                _repay.emit(it)
            }
        }
    }
}