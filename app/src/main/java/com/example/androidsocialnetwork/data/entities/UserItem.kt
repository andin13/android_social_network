package com.example.androidsocialnetwork.data.entities

data class UserItem(
    val followed: Boolean,
    val id: Int,
    val name: String,
    val photos: Photos,
    val status: String?,
    val uniqueUrlName: String?
)