package com.example.androidsocialnetwork.data.entities

data class GetUsersResponseType(
    val error: String,
    val items: List<UserItem>,
    val totalCount: Int
)