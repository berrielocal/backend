package ru.vsu.cs.berrielocal.dto.comment

data class CommentResponse(
    var commentId: Long? = null,
    var customerId: Long? = null,
    var shopId: Long? = null,
    var text: String? = null,
    var rate: Double? = null
)
