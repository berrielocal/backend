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
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.cart.OrderPartListResponse
import ru.vsu.cs.berrielocal.dto.cart.ProductAddToCartRequest
import ru.vsu.cs.berrielocal.security.JwtTokenProvider
import ru.vsu.cs.berrielocal.service.CartService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "CartController", description = "Работа с корзиной покупателя")
class CartController(
    private val cartService: CartService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @GetMapping("/cart")
    @Operation(summary = "Просмотр всех объектов в корзине покупателя")
    fun getAllItemsInCart(
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<OrderPartListResponse> {
        val customerId = jwtTokenProvider.getCustomClaimValue(token, "id").toLong()

        val response = cartService.getAllProductsFromCart(customerId)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/cart")
    @Operation(summary = "Добавление продукта в корзину покупателя")
    fun addNewItemToCart(
        @RequestBody item: ProductAddToCartRequest,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        val customerId = jwtTokenProvider.getCustomClaimValue(token, "id").toLong()
        cartService.addProductToCart(item, customerId)

        return ResponseEntity.ok().build<Any>()
    }

    @PutMapping("/cart/item/{productId}/{size}")
    @Operation(summary = "Изменение размера объекта в корзине")
    fun changeSizeOnProductInCart(
        @PathVariable productId: Long,
        @PathVariable size: Long,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        jwtTokenProvider.getCustomClaimValue(token, "id")
        cartService.changeSizeOnProductInCart(productId, size)

        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/cart/item/{productId}")
    @Operation(summary = "Удаление объекта из корзины")
    fun changeSizeOnProductInCart(
        @PathVariable productId: Long,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        jwtTokenProvider.getCustomClaimValue(token, "id")
        cartService.removeProductFromCart(productId)

        return ResponseEntity.ok().build<Any>()
    }
}
