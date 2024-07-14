package com.shashank.splitterexpensemanager.feature.total

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.total.repository.TotalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TotalViewModel @Inject constructor(
    private val totalRepository: TotalRepository
) : ViewModel() {

    private val _totalGroupSpending = MutableStateFlow<Double>(0.0)
    val totalGroupSpending = _totalGroupSpending.asStateFlow()

    private val _totalGroupSpendingForByMonthAndYear = MutableStateFlow<Double>(0.0)
    val totalGroupSpendingForByMonthAndYear = _totalGroupSpendingForByMonthAndYear.asStateFlow()

    private val _totalYouPaidFor = MutableStateFlow<Double>(0.0)
    val totalYouPaidFor = _totalYouPaidFor.asStateFlow()

    private val _totalYouPaidForForByMonthAndYear = MutableStateFlow<Double>(0.0)
    val totalYouPaidForForByMonthandYear = _totalYouPaidForForByMonthAndYear.asStateFlow()

    private val _yourTotalShare = MutableStateFlow<Double>(0.0)
    val yourTotalShare = _yourTotalShare.asStateFlow()

    private val _yourTotalShareByMonthAndYear = MutableStateFlow<Double>(0.0)
    val yourTotalShareByMonthAndYear = _yourTotalShareByMonthAndYear.asStateFlow()
    fun getTotalGroupSpending(groupId: Long) {
        viewModelScope.launch {
            totalRepository.getTotalGroupSpending(groupId).collect {
                _totalGroupSpending.emit(it)
            }
        }
    }

    fun getTotalGroupSpendingByMonthAndYear(groupId: Long, month: String, year: String) {
        viewModelScope.launch {
            totalRepository.getTotalGroupSpendingByMonthAndYear(groupId, month, year).collect {
                _totalGroupSpendingForByMonthAndYear.emit(it)
            }
        }
    }

    fun getTotalYouPaidFor(groupId: Long, personId: Long) {
        viewModelScope.launch {
            totalRepository.getTotalYouPaidFor(personId, groupId).collect {
                _totalYouPaidFor.emit(it)
            }
        }
    }

    fun getTotalYouPaidForByMonthAndYear(
        groupId: Long,
        personId: Long,
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            totalRepository.getTotalYouPaidForByMonthAndYear(personId, groupId, month, year)
                .collect {
                    _totalYouPaidForForByMonthAndYear.emit(it)
                }
        }
    }


    fun getYourTotalShare(groupId: Long) {
        viewModelScope.launch {
            totalRepository.getYouTotalShare(groupId).collect {
                _yourTotalShare.emit(it)
            }
        }
    }

    fun getYourTotalShareByMonthAndYear(groupId: Long, month: String, year: String) {
        viewModelScope.launch {
            totalRepository.getYouTotalShareByMonthAndYear(groupId, month, year).collect {
                _yourTotalShareByMonthAndYear.emit(it)
            }
        }
    }
}