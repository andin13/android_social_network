package com.example.androidsocialnetwork.data.entities

data class LoginData(
    val email: String,
    val password: String,
    val rememberMe: Boolean,
)