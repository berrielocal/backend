package ru.vsu.cs.berrielocal.dto.shop

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ShopUpdateRequest(
    val name: String? = null,
    val imageUrl: String? = null,
    val matchLevel: Double? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
)
