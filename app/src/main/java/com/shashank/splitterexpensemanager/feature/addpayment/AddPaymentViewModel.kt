package com.shashank.splitterexpensemanager.feature.addpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.addpayment.repository.AddPaymentRepository
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.Repay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPaymentViewModel @Inject constructor(
    private val addPaymentRepository: AddPaymentRepository
) : ViewModel() {

    private val _repayInsertSuccessfully = MutableStateFlow<Boolean>(false)
    val repayInsertSuccessfully = _repayInsertSuccessfully.asStateFlow()

    private val _payer = MutableStateFlow<Person?>(null)
    val payer = _payer.asStateFlow()

    private val _receiver = MutableStateFlow<Person?>(null)
    val receiver = _receiver.asStateFlow()
    suspend fun insertRepay(
        payerId: Long,
        receiverId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
    ) = viewModelScope.launch {
        val repayId = addPaymentRepository.insertRepay(
            Repay(
                null,
                payerId,
                receiverId,
                groupId,
                amount,
                date,
                time,
                description,
            )
        )
        addPaymentRepository.insertOweOrOwed(
            OweOrOwed(
                null,
                null,
                repayId,
                payerId,
                receiverId,
                groupId,
                amount
            )
        )
        _repayInsertSuccessfully.emit(true)
    }

    fun getReceiver(personId: Long) {
        viewModelScope.launch {
            addPaymentRepository.loadPersonByPersonId(personId).collect {
                _receiver.emit(it)
            }
        }
    }

    fun getPayer(personId: Long) {
        viewModelScope.launch {
            addPaymentRepository.loadPersonByPersonId(personId).collect {
                _payer.emit(it)
            }
        }
    }
}