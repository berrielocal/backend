package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.berrielocal.model.OrderPart
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus

interface OrderPartRepository : JpaRepository<OrderPart, Long> {

    fun findAllByCustomerIdAndStatus(customerId: Long, status: OrderPartStatus): List<OrderPart>
}