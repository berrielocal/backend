package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vsu.cs.berrielocal.model.Shop
import ru.vsu.cs.berrielocal.model.enums.Category

@Repository
interface ShopRepository : JpaRepository<Shop, Long> {

    fun findByEmail(email: String?): Shop?
}