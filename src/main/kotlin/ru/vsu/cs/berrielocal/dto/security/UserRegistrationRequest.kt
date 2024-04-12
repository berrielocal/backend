package ru.vsu.cs.berrielocal.dto.security

data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val phoneNumber: String? = null,
    val imageUrl: String? = null
)