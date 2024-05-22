package ru.vsu.cs.berrielocal.dto.cart

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ProductAddToCartRequest(
    val size: Long = 1,
    val productId: Long,
)