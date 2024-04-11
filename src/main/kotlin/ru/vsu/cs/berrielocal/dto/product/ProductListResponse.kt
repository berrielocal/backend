package ru.vsu.cs.berrielocal.dto.product

data class ProductListResponse(
    val shopId: Long? = null,
    val products: List<ProductResponse>? = null
)
