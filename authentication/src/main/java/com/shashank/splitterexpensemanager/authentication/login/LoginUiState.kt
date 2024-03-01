package com.shashank.splitterexpensemanager.authentication.login

import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val user: FirebaseUser? = null,
)