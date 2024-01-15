package com.shashank.splitterexpensemanager.core.network

sealed interface NetworkCallState {
    object Loading : NetworkCallState
    object Init : NetworkCallState
    object Success : NetworkCallState
    data class Error(val errorMsg: String) : NetworkCallState
}