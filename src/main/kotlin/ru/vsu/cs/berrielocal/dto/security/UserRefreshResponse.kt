package ru.vsu.cs.berrielocal.dto.security

data class UserRefreshResponse(
    val accessToken: String,
    val refreshToken: String
)
