package ru.vsu.cs.berrielocal.dto.order

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus

@Schema
data class ChangeOrderStatusRequest(
    val orderPartId: Long? = null,
    val newStatus: OrderPartStatus? = OrderPartStatus.ORDERED
)
