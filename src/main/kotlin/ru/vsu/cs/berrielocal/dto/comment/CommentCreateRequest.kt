package ru.vsu.cs.berrielocal.dto.comment

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class CommentCreateRequest(
    val customerId: Long?,
    val shopId: Long?,
    val text: String?,
    val rate: Double?
)
