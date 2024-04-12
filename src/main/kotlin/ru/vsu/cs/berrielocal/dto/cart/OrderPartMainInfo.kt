package ru.vsu.cs.berrielocal.dto.cart

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class OrderPartMainInfo(
    var productId: Long? = null,
    var size: Long? = null
)
