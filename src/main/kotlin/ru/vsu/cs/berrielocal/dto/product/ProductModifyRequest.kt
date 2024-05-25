package ru.vsu.cs.berrielocal.dto.product

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.validation.ProductArgsValidation

@Schema
@ProductArgsValidation
data class ProductModifyRequest(
    val name: String?,
    val imageUrl: String?,
    val cost: Double?,
    val maxSize: Long?,
    val minSize: Long?,
    val units: String?,
    var categories: Set<Category>? = null
)