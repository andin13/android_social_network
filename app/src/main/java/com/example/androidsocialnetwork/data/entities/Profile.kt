package com.example.androidsocialnetwork.data.entities

data class Profile(
    val aboutMe: String,
    val contacts: Contacts,
    val fullName: String,
    val lookingForAJob: Boolean,
    val lookingForAJobDescription: String,
    val photos: PhotoUrls?,
    val userId: Int
)