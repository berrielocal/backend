package ru.vsu.cs.berrielocal.dto.cart

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus
import java.time.LocalDateTime

@Schema
data class OrderPartMainInfo(
    var productId: Long? = null,
    var size: Long? = null,
    var status: OrderPartStatus = OrderPartStatus.IN_CART,
    var updatedAt: LocalDateTime? = null,
)
