package com.example.authentication.login

import com.google.firebase.auth.FirebaseUser

data class LoginUiState(
    val user: FirebaseUser? = null,
)