package ru.vsu.cs.berrielocal.dto.comment

data class CommentResponse(
    val commentId: Long? = null,
    var customerId: Long? = null,
    var shopId: Long? = null,
    val text: String? = null,
    val rate: Double? = null
)
