package ru.vsu.cs.berrielocal.dto.order

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.dto.cart.OrderPartMainInfo

@Schema
data class OrderPartByShopIdResponse(
    val orderParts: List<OrderPartMainInfo>
)
