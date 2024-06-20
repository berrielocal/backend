package ru.vsu.cs.berrielocal.service

import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.cart.OrderPartListResponse
import ru.vsu.cs.berrielocal.dto.cart.ProductAddToCartRequest
import ru.vsu.cs.berrielocal.dto.cart.ProductAddToCartResponse
import ru.vsu.cs.berrielocal.exception.OrderPartNotFoundException
import ru.vsu.cs.berrielocal.exception.ProductAddToCartException
import ru.vsu.cs.berrielocal.mapper.OrderPartMapper
import ru.vsu.cs.berrielocal.model.OrderPart
import ru.vsu.cs.berrielocal.model.Product
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus
import ru.vsu.cs.berrielocal.repository.OrderPartRepository
import java.time.LocalDateTime

@Service
class CartService(
    private val productService: ProductService,
    private val shopService: ShopService,
    private val orderPartRepository: OrderPartRepository,
    private val mapper: OrderPartMapper
) {

    fun getAllProductsFromCart(customerId: Long): OrderPartListResponse {
        val items = orderPartRepository.findAllByCustomerIdAndStatus(customerId, OrderPartStatus.IN_CART)
            .map(mapper::toMainInfo)

        val sum = items.filter { it.productId != null }.sumOf {
            productService.getById(it.productId!!).cost?.let { it1 -> it.size?.times(it1) } ?: 0.0
        }.toBigDecimal()

        return OrderPartListResponse(items, sum)
    }

    fun addProductToCart(request: ProductAddToCartRequest, customerId: Long): ProductAddToCartResponse {
        val foundedProduct = productService.getByIdEntity(request.productId)

        ifReceivedSizeNotBetweenMaxAndMinOfProductThrowException(request.size, foundedProduct)

        if (shopService.getById(customerId) == null)
            throw ProductAddToCartException("Not found customer for id:${customerId}")

        val orderPart = OrderPart().apply {
            this.status = OrderPartStatus.IN_CART
            this.product = foundedProduct
            this.size = request.size
            this.customerId = customerId
            this.updatedAt = LocalDateTime.now()
        }

        val orderEntity = orderPartRepository.save(orderPart)

        return ProductAddToCartResponse(orderEntity.orderPartId)
    }

    fun changeSizeOnProductInCart(productId: Long, newSize: Long, customerId: Long) {
        val orderPart = orderPartRepository.findByProductIdAndCustomerIdInCart(productId, customerId)
            ?: throw OrderPartNotFoundException("Not found product in cart with id:$productId")

        if (orderPart.status != OrderPartStatus.IN_CART)
            throw OrderPartNotFoundException("Found product not in cart, orderPartId:${productId}")

        ifReceivedSizeNotBetweenMaxAndMinOfProductThrowException(newSize, orderPart.product!!)

        orderPartRepository.save(orderPart.apply {
            size = newSize
        })
    }

    fun removeProductFromCart(productId: Long, customerId: Long) {
        val entity = orderPartRepository.findByProductIdAndCustomerIdInCart(productId, customerId)

        entity?.orderPartId?.let {
            orderPartRepository.deleteById(it)
        }
    }

    private fun ifReceivedSizeNotBetweenMaxAndMinOfProductThrowException(receivedSize: Long, product: Product) {
        val minSize = product.minSize ?: 0
        val maxSize = product.maxSize ?: Long.MAX_VALUE

        if (receivedSize !in minSize..maxSize)
            throw ProductAddToCartException("Not added product to cart with current request size:$receivedSize")
    }
}