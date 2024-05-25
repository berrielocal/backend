package ru.vsu.cs.berrielocal.dto.security

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class UserRefreshRequest(
    val refreshToken: String?
)
