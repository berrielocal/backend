package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.order.ChangeOrderStatusRequest
import ru.vsu.cs.berrielocal.dto.order.OrderCreateRequest
import ru.vsu.cs.berrielocal.dto.order.OrderPartByShopIdResponse
import ru.vsu.cs.berrielocal.service.OrderService

@RestController
@RequestMapping(API_VERSION)
@Tag(name = "OrderController", description = "Работа с заказами")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("/order/accept")
    @Operation(summary = "Создание заказа")
    fun acceptOrder(@RequestBody request: OrderCreateRequest): ResponseEntity<*> {
        orderService.acceptOrder(request)

        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping("order/customer/{customerId}")
    @Operation(summary = "Получени всех активных заказов по покупателю")
    fun getAllActiveOrdersByCustomerId(@PathVariable customerId: Long): ResponseEntity<OrderPartByShopIdResponse> {
        val response = orderService.getAllActiveOrdersByCustomerId(customerId)

        return ResponseEntity.ok(response)
    }

    @GetMapping("order/shop/{shopId}")
    @Operation(summary = "Получение всех активных заказов по продавцу")
    fun getAllActiveOrdersByShopId(@PathVariable shopId: Long): ResponseEntity<OrderPartByShopIdResponse> {
        val response = orderService.getActiveOrdersForShopId(shopId)

        return ResponseEntity.ok(response)
    }

    @PutMapping("order/shop")
    @Operation(summary = "Изменение статуса у заказа")
    fun changeStatusOnOrderPart(@RequestBody request: ChangeOrderStatusRequest): ResponseEntity<*> {
        orderService.setNewStatusToOrderPartById(request)

        return ResponseEntity.ok().build<Any>()
    }
}