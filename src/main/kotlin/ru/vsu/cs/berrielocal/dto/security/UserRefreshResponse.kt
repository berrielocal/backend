package ru.vsu.cs.berrielocal.dto.security

data class UserRefreshResponse(
    val refreshToken: String,
    val accessToken: String
)
