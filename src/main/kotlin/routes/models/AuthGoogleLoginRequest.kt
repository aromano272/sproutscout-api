package com.tometrics.api.routes.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthGoogleLoginRequest(
    val csrfToken: String,
    val idToken: String
)