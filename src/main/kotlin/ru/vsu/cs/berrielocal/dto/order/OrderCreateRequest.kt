package ru.vsu.cs.berrielocal.dto.order

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class OrderCreateRequest(
    val address: String? = null
)
