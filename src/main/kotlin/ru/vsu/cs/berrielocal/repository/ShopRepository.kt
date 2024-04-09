package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vsu.cs.berrielocal.model.Shop

@Repository
interface ShopRepository : JpaRepository<Shop, Long> {

    fun findByEmail(email: String?): Shop?
}