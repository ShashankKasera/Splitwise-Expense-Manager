package com.example.authentication.registration

import com.google.firebase.auth.FirebaseUser

data class RegistrationUiState(
    val user: FirebaseUser? = null,
)
