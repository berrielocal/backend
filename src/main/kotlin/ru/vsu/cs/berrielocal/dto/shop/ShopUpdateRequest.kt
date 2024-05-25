package ru.vsu.cs.berrielocal.dto.shop

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.Category

@Schema
data class ShopUpdateRequest(
    val name: String? = null,
    val imageUrl: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val categories: Set<Category>? = null,
)
