package ru.vsu.cs.berrielocal.dto.security

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)