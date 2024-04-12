package ru.vsu.cs.berrielocal.dto.product

data class ProductResponse(
    val productId: Long? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val cost: Double? = null,
    val maxSize: Long? = null,
    val minSize: Long? = null,
    val units: String? = null,
    var shopId: Long? = null
)
