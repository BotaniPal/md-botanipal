package com.botanipal.botanipal.data.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
