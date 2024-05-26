package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vsu.cs.berrielocal.model.Comment

interface CommentRepository : JpaRepository<Comment, Long> {

    @Query(
        nativeQuery = true,
        value = """
            SELECT * FROM comments c
            WHERE c.seller_shop_id = :shopId
        """
    )
    fun findAllByShopId(shopId: Long): List<Comment>

    @Query(
        nativeQuery = true,
        value = """
            SELECT ROUND(cast(AVG(rate) as numeric), 2) FROM comments c
            WHERE c.seller_shop_id = :sellerId and c.rate is not null
        """
    )
    fun findAverageRateBySellerId(sellerId: Long?): Double?
}