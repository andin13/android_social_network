package com.example.androidsocialnetwork.data.entities

data class LoginResponseType(
    val data: UserID,
    val messages: List<Any>,
    val resultCode: Int
)