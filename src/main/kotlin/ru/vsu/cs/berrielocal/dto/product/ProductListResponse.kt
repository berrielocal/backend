package ru.vsu.cs.berrielocal.dto.product

import ru.vsu.cs.berrielocal.model.enums.Category

data class ProductListResponse(
    val shopId: Long? = null,
    val products: Map<Category, List<ProductResponse>?>? = null
)
