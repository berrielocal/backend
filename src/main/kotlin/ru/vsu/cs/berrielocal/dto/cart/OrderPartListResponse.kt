package ru.vsu.cs.berrielocal.dto.cart

import java.math.BigDecimal

data class OrderPartListResponse(
    val items: List<OrderPartMainInfo>,
    val sum: BigDecimal? = null,
)
