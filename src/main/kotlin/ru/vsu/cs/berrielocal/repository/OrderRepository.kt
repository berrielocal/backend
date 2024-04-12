package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.berrielocal.model.Order

interface OrderRepository : JpaRepository<Order, Long>

