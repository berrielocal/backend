package ru.vsu.cs.berrielocal.dto.shop

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.Category

@Schema
data class ShopListResponse(
    val shops: Map<Category, List<ShopMainInfo>?>,
)
