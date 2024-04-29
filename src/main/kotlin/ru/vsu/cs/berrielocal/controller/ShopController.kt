package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.product.EntityByCategoriesRequest
import ru.vsu.cs.berrielocal.dto.shop.ShopAllInfoResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopListResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopUpdateRequest
import ru.vsu.cs.berrielocal.service.ShopService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "ShopController", description = "Работа с данными магазинов")
class ShopController(
    private val shopService: ShopService
) {

    @GetMapping("/shop")
    @Operation(summary = "Получение всех магазинов")
    fun getShops(@RequestBody request: EntityByCategoriesRequest): ResponseEntity<ShopListResponse> {
        val shops = shopService.getShopsList(request.categories)

        return ResponseEntity.ok(shops)
    }

    @GetMapping("/shop/{shopId}")
    @Operation(
        summary = "Получение информации о магазине по ID",
        description = "В качестве входных параметров - customerId. " +
                "Относительно него расчитывается уровня совпадения интересов магазина и покупателя - matchLevel"
    )
    fun getShopById(
        @PathVariable shopId: Long,
        @RequestParam customerId: Long
    ): ResponseEntity<ShopAllInfoResponse> {
        val shop = shopService.getByShopId(shopId, customerId)

        return ResponseEntity.ok(shop)
    }

    @PatchMapping("/shop/{shopId}")
    @Operation(summary = "Изменение информации о магазине по ID")
    fun updateShop(
        @PathVariable shopId: Long,
        @RequestBody shop: ShopUpdateRequest
    ): ResponseEntity<*> {
        shopService.updateById(shopId, shop)

        return ResponseEntity.ok().build<Any>()
    }
}