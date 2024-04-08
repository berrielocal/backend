package ru.vsu.cs.berrielocal.dto.security

data class UserRegistrationRequest(
    val email: String,
    val password: String
)