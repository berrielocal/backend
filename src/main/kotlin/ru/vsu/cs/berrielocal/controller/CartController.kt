package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.cart.OrderPartListResponse
import ru.vsu.cs.berrielocal.dto.cart.ProductAddToCartRequest
import ru.vsu.cs.berrielocal.service.CartService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "CartController", description = "Работа с корзиной покупателя")
class CartController(
    private val cartService: CartService
) {

    @GetMapping("/cart/{customerId}")
    @Operation(summary = "Просмотр всех объектов в корзине покупателя")
    fun getAllItemsInCart(@PathVariable customerId: Long): ResponseEntity<OrderPartListResponse> {
        val response = cartService.getAllProductsFromCart(customerId)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/cart/{customerId}")
    @Operation(summary = "Добавление продукта в корзину покупателя")
    fun addNewItemToCart(
        @PathVariable customerId: Long,
        @RequestBody item: ProductAddToCartRequest
    ): ResponseEntity<*> {
        cartService.addProductToCart(item)

        return ResponseEntity.ok().build<Any>()
    }

    @PutMapping("/cart/item/{orderPartId}/{size}")
    @Operation(summary = "Изменение размера объекта в корзине")
    fun changeSizeOnProductInCart(
        @PathVariable orderPartId: Long,
        @PathVariable size: Long,
    ): ResponseEntity<*> {
        cartService.changeSizeOnProductInCart(orderPartId, size)

        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/cart/item/{orderPartId}")
    @Operation(summary = "Удаление объекта из корзины")
    fun changeSizeOnProductInCart(
        @PathVariable orderPartId: Long
    ): ResponseEntity<*> {
        cartService.removeProductFromCart(orderPartId)

        return ResponseEntity.ok().build<Any>()
    }
}
