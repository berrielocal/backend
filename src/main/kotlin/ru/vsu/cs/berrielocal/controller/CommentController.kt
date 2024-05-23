package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.comment.CommentCreateRequest
import ru.vsu.cs.berrielocal.dto.comment.CommentsResponse
import ru.vsu.cs.berrielocal.security.JwtTokenProvider
import ru.vsu.cs.berrielocal.service.CommentService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "Comment Controller", description = "Работа с комментариями")
class CommentController(
    private val commentService: CommentService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @GetMapping("/comment/shop/{shopId}")
    @Operation(summary = "Просмотр всех комментариев магазина по id магазина")
    fun getAllCommentsByShopId(
        @PathVariable shopId: Long
    ): ResponseEntity<CommentsResponse> {
        val comments = commentService.getAllComments(shopId)

        return ResponseEntity.ok(comments)
    }

    @PostMapping("/comment")
    @Operation(summary = "Сохранение нового комментария покупателя")
    fun saveNewComment(
        @RequestBody request: CommentCreateRequest,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        val customerId = jwtTokenProvider.getCustomClaimValue(token, "id").toLong()
        commentService.saveComment(request, customerId)

        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "Удаление комментария по commentId")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        jwtTokenProvider.getCustomClaimValue(token, "id")
        commentService.deleteComment(commentId)

        return ResponseEntity.ok().build<Any>()
    }
}