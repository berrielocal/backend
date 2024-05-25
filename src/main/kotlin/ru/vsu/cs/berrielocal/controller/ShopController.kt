package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.product.EntityByCategoriesRequest
import ru.vsu.cs.berrielocal.dto.shop.ShopAllInfoResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopListResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopUpdateRequest
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.ShopRepository
import ru.vsu.cs.berrielocal.security.JwtTokenProvider
import ru.vsu.cs.berrielocal.service.ShopService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "ShopController", description = "Работа с данными магазинов")
class ShopController(
    private val shopService: ShopService,
    private val jwtTokenProvider: JwtTokenProvider, private val shopRepository: ShopRepository
) {

    @GetMapping("/shop")
    @Operation(summary = "Получение всех магазинов")
    fun getShops(@RequestParam category: List<Category>?): ResponseEntity<ShopListResponse> {
        val shops = shopService.getShopsList(category)

        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shop/{shopId}")
    @Operation(
        summary = "Получение информации о магазине по ID",
        description = "В качестве входных параметров - customerId. Берется из токена. " +
                "Если токен не валиден, то matchLevel = 0" +
                "Относительно него расчитывается уровня совпадения интересов магазина и покупателя - matchLevel"
    )
    fun getShopById(
        @PathVariable shopId: Long,
        @RequestHeader(name = "AccessToken") authToken: String?,
    ): ResponseEntity<ShopAllInfoResponse> {

        val customerId =
            runCatching {
                authToken?.let {
                    jwtTokenProvider.getCustomClaimValue(it, "id").toLong()
                }
            }.getOrNull()

        val shop = shopService.getByShopId(shopId, customerId)

        return ResponseEntity.ok(shop)
    }

    @PatchMapping("/shop")
    @Operation(summary = "Изменение информации о магазине по ID")
    fun updateShop(
        @RequestBody shop: ShopUpdateRequest,
        @RequestHeader("AccessToken") token: String
    ): ResponseEntity<*> {
        val shopId = jwtTokenProvider.getCustomClaimValue(token, "id").toLong()

        shopService.updateById(shopId, shop)

        return ResponseEntity.ok().build<Any>()
    }
}