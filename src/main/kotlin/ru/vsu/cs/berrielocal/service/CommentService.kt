package ru.vsu.cs.berrielocal.service

import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.comment.CommentCreateRequest
import ru.vsu.cs.berrielocal.dto.comment.CommentsResponse
import ru.vsu.cs.berrielocal.exception.CommentCreateException
import ru.vsu.cs.berrielocal.mapper.CommentMapper
import ru.vsu.cs.berrielocal.model.Comment
import ru.vsu.cs.berrielocal.repository.CommentRepository

@Service
class CommentService(
    private val mapper: CommentMapper,
    private val commentRepository: CommentRepository,
    private val shopService: ShopService
) {
    fun getAllComments(shopId: Long): CommentsResponse? {
        val comments = commentRepository.findAllByShopId(shopId).map(mapper::toCommentResponse)

        return CommentsResponse(comments)
    }

    fun saveComment(request: CommentCreateRequest, customerId: Long)  {
        val customerFromDb = shopService.getById(customerId)
        val shopFromDb = shopService.getById(request.shopId)

        if (customerFromDb == null)
            throw CommentCreateException("Not found customer by id:${customerId}")
        if (shopFromDb == null)
            throw CommentCreateException("Not found shop by id:${customerId}")

        if (request.rate == null || request.rate < 0)
            throw CommentCreateException("Not saved comment with illegal argument rate:${request.rate}")

        val commentToSave = Comment().apply {
            customer = customerFromDb
            seller = shopFromDb
            rate = request.rate
            text = request.text
        }
        commentRepository.save(commentToSave)
    }

    fun deleteComment(commentId: Long) {
        commentRepository.deleteById(commentId)
    }

}
