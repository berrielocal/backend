package ru.vsu.cs.berrielocal.dto.shop

import ru.vsu.cs.berrielocal.model.enums.Category

data class ShopAllInfoResponse(
    var shopId: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var matchLevel: Double? = null,
    var categories: Set<Category>? = null
)
