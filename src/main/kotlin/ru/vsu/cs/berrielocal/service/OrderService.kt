package ru.vsu.cs.berrielocal.service

import jakarta.transaction.Transactional
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.order.ChangeOrderStatusRequest
import ru.vsu.cs.berrielocal.dto.order.OrderCreateRequest
import ru.vsu.cs.berrielocal.dto.order.OrderPartByShopIdResponse
import ru.vsu.cs.berrielocal.exception.ChangeOrderPartStatusException
import ru.vsu.cs.berrielocal.exception.OrderCreateException
import ru.vsu.cs.berrielocal.mapper.OrderPartMapper
import ru.vsu.cs.berrielocal.model.Order
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus
import ru.vsu.cs.berrielocal.repository.OrderPartRepository
import ru.vsu.cs.berrielocal.repository.OrderRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderPartRepository: OrderPartRepository,
    private val shopService: ShopService,
    private val mapper: OrderPartMapper
) {

    @Transactional
    fun acceptOrder(request: OrderCreateRequest) {
        val customerFromDb = shopService.getById(request.customerId)
            ?: throw OrderCreateException("Order not created, not found customer for id:${request.customerId}")


        val items = orderPartRepository.findAllByCustomerIdAndStatus(customerFromDb.shopId!!, OrderPartStatus.IN_CART)

        if (items.isEmpty())
            throw OrderCreateException(
                "Order not created: " +
                        "not found any item in cart for customerId:${request.customerId}"
            )

        val order = Order().apply {
            address = request.address
            customer = customerFromDb
        }

        val orderFromDb = orderRepository.save(order)

        items.forEach {
            it.apply {
                this.status = OrderPartStatus.ORDERED
                this.order = orderFromDb
            }
            orderPartRepository.save(it)
        }
    }

    fun getActiveOrdersForShopId(shopId: Long): OrderPartByShopIdResponse {
        val items = orderPartRepository.findAllActiveByShopId(shopId).map(mapper::toMainInfo).filter {
            it.status != OrderPartStatus.IN_CART
        }

        return OrderPartByShopIdResponse(items)
    }

    fun setNewStatusToOrderPartById(request: ChangeOrderStatusRequest) {
        if (request.orderPartId == null)
            throw ChangeOrderPartStatusException("Can not change status on order, be cause not received orderPartId")

        val orderPartId = request.orderPartId
        val newStatus = request.newStatus

        if (newStatus == OrderPartStatus.IN_CART)
            throw ChangeOrderPartStatusException("Can not return order item back to cart")


        val orderPartInDb = orderPartRepository.findById(orderPartId).getOrNull()
            ?: throw ChangeOrderPartStatusException("Not found item by id:$orderPartId")


        orderPartInDb.apply {
            status = newStatus
        }

        orderPartRepository.save(orderPartInDb)
    }

    fun getAllActiveOrdersByCustomerId(customerId: Long): OrderPartByShopIdResponse {
        val items = orderPartRepository.findAllByCustomerId(customerId)
            .filter {
                it.status != OrderPartStatus.IN_CART
            }.map(mapper::toMainInfo)


        return OrderPartByShopIdResponse(items)
    }
}