package ru.vsu.cs.berrielocal.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import ru.vsu.cs.berrielocal.dto.comment.CommentResponse
import ru.vsu.cs.berrielocal.model.Comment

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface CommentMapper {

    @Mapping(source = "customer.shopId", target = "customerId")
    @Mapping(source = "seller.shopId", target = "shopId")
    fun toCommentResponse(source: Comment): CommentResponse
}