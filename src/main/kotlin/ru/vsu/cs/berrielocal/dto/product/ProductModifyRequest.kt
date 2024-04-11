package ru.vsu.cs.berrielocal.dto.product

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.validation.ProductArgsValidation

@Schema
@ProductArgsValidation
data class ProductModifyRequest(
    val name: String?,
    val imageUrl: String?,
    val cost: Double?,
    val maxSize: Double?,
    val minSize: Double?,
    val units: String?,
    val shopId: Long?
)