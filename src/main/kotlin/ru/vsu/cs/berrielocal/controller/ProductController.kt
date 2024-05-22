package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.product.ProductCreateResponse
import ru.vsu.cs.berrielocal.dto.product.ProductModifyRequest
import ru.vsu.cs.berrielocal.dto.product.ProductListResponse
import ru.vsu.cs.berrielocal.dto.product.ProductResponse
import ru.vsu.cs.berrielocal.security.JwtTokenProvider
import ru.vsu.cs.berrielocal.service.ProductService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "ProductController", description = "Работа с данными продуктов")
class ProductController(
    private val productService: ProductService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @GetMapping("/product/shop/{shopId}")
    @Operation(summary = "Получение продуктов по id магазина")
    fun getProductByShopId(@PathVariable shopId: Long): ResponseEntity<ProductListResponse> {
        val products = productService.getProductsByShopId(shopId)

        return ResponseEntity.ok(products)
    }

    @GetMapping("/product/{productId}")
    @Operation(
        summary = "Получение информации о продукте по ID",
    )
    fun getShopById(
        @PathVariable productId: Long,
    ): ResponseEntity<ProductResponse> {
        val product = productService.getById(productId)

        return ResponseEntity.ok(product)
    }

    @PostMapping("/product")
    @Operation(summary = "Добавление продукта в магазин")
    fun updateProduct(
        @Valid @RequestBody product: ProductModifyRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<ProductCreateResponse> {
        jwtTokenProvider.getCustomClaimValue(token, "id")

        val response = productService.create(product)

        return ResponseEntity.ok(response)
    }

    @PatchMapping("/product/{productId}")
    @Operation(summary = "Изменение информации о продукте по ID")
    fun updateProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody product: ProductModifyRequest,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        jwtTokenProvider.getCustomClaimValue(token, "id")

        productService.updateById(productId, product)

        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/product/{productId}")
    @Operation(summary = "Удаление продукта")
    fun deleteProduct(
        @PathVariable productId: Long,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<*> {
        jwtTokenProvider.getCustomClaimValue(token, "id")

        productService.deleteById(productId)

        return ResponseEntity.ok().build<Any>()
    }
}