package ru.vsu.cs.berrielocal.dto.shop

import ru.vsu.cs.berrielocal.model.enums.Category

data class ShopListResponse(
    val shops: Map<Category, List<ShopMainInfo>?>
)
