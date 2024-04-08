package ru.vsu.cs.berrielocal.dto.security

data class UserAuthorizationRequest(
    val email: String,
    val password: String
)
