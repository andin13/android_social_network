package com.example.androidsocialnetwork.data.entities

import com.google.gson.annotations.SerializedName

data class AuthMeResponseType(
    @SerializedName("data") val data: User,
    val fieldsErrors: List<Any>,
    val messages: List<String>,
    val resultCode: Int
)