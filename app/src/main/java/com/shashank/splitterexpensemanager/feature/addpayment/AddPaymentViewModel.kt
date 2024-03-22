package com.shashank.splitterexpensemanager.feature.addpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.feature.addpayment.repository.AddPaymentRepository
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.model.OweOrOwed
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity

@HiltViewModel
class AddPaymentViewModel @Inject constructor(
    private val addPaymentRepository: AddPaymentRepository
) : ViewModel() {

    var oweOrOwed = OweOrOwed()

    private val _oweOrOwedFetchedForUpdate = MutableStateFlow<Boolean>(false)
    val oweOrOwedFetchedForUpdate = _oweOrOwedFetchedForUpdate.asStateFlow()

    private val _repayInsertSuccessfully = MutableStateFlow<Boolean>(false)
    val repayInsertSuccessfully = _repayInsertSuccessfully.asStateFlow()

    private val _payer = MutableStateFlow<Person?>(null)
    val payer = _payer.asStateFlow()

    private val _receiver = MutableStateFlow<Person?>(null)
    val receiver = _receiver.asStateFlow()

    private val _repayDetails = MutableStateFlow<RepayWithPerson?>(null)
    val repayDetails = _repayDetails.asStateFlow()
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
            OweOrOwedEntity(
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

    suspend fun updateRepay(
        repayId: Long,
        payerId: Long,
        receiverId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
    ) = viewModelScope.launch {
        addPaymentRepository.updateRepay(
            Repay(
                repayId,
                payerId,
                receiverId,
                groupId,
                amount,
                date,
                time,
                description,
            )
        )

        addPaymentRepository.updateOweOrOwed(
            OweOrOwedEntity(
                oweOrOwed.id,
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

    fun loadRepayDetails(repayId: Long) {
        viewModelScope.launch {
            addPaymentRepository.loadRepayByRepayId(repayId).collect {
                _repayDetails.emit(it)
            }
        }
    }

    fun loadOweOrOwed(repayId: Long) {
        viewModelScope.launch {
            addPaymentRepository.loadOweOrOwedByRepayId(repayId).collect {
                oweOrOwed = it
                _oweOrOwedFetchedForUpdate.emit(true)
            }
        }
    }
}