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

        return OrderPartListResponse(items)
    }

    fun addProductToCart(request: ProductAddToCartRequest): ProductAddToCartResponse {
        val foundedProduct = productService.getByIdEntity(request.productId)

        ifReceivedSizeNotBetweenMaxAndMinOfProductThrowException(request.size, foundedProduct)

        if (shopService.getById(request.customerId) == null)
            throw ProductAddToCartException("Not found customer for id:${request.customerId}")

        val orderPart = OrderPart().apply {
            status = OrderPartStatus.IN_CART
            product = foundedProduct
            size = request.size
            customerId = request.customerId
        }

        val orderEntity = orderPartRepository.save(orderPart)

        return ProductAddToCartResponse(orderEntity.orderPartId)
    }

    fun changeSizeOnProductInCart(productId: Long, newSize: Long) {
        val orderPart = orderPartRepository.findByProductId(productId)
            ?: throw OrderPartNotFoundException("Not found product in cart with id:$productId")

        if (orderPart.status != OrderPartStatus.IN_CART)
            throw OrderPartNotFoundException("Found product not in cart, orderPartId:${productId}")

        ifReceivedSizeNotBetweenMaxAndMinOfProductThrowException(newSize, orderPart.product!!)

        orderPartRepository.save(orderPart.apply {
            size = newSize
        })
    }

    fun removeProductFromCart(productId: Long) {
        val entity = orderPartRepository.findByProductId(productId)
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