package ru.vsu.cs.berrielocal.dto.product

import io.swagger.v3.oas.annotations.media.Schema
import ru.vsu.cs.berrielocal.model.enums.Category

@Schema
data class EntityByCategoriesRequest(
    val categories: List<Category>?
)
