package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vsu.cs.berrielocal.model.OrderPart
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus

interface OrderPartRepository : JpaRepository<OrderPart, Long> {

    fun findAllByCustomerIdAndStatus(customerId: Long, status: OrderPartStatus): List<OrderPart>

    fun findAllByCustomerId(customerId: Long): List<OrderPart>

    @Query(
        nativeQuery = true,
        value = """
            SELECT * FROM order_parts op
            JOIN products p ON op.product_product_id = p.product_id
            WHERE p.shop_shop_id = :shopId
        """
    )
    fun findAllActiveByShopId(shopId: Long): List<OrderPart>

    @Query(
        nativeQuery = true,
        value = """
            SELECT * FROM order_parts op
            WHERE op.product_product_id = :productId
            AND op.status != 'IN_CART'
        """
    )
    fun findByProductId(productId: Long): OrderPart?
}