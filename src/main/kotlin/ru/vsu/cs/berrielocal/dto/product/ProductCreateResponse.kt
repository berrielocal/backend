package ru.vsu.cs.berrielocal.dto.product

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ProductCreateResponse(
    val productId: Long
)
