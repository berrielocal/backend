package ru.vsu.cs.berrielocal.dto.product

import ru.vsu.cs.berrielocal.model.enums.Category

data class ProductResponse(
    var productId: Long? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var cost: Double? = null,
    var maxSize: Long? = null,
    var minSize: Long? = null,
    var units: String? = null,
    var shopId: Long? = null,
    var categories: Set<Category>? = null
)
