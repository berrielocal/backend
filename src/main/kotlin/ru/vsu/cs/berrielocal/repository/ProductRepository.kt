package ru.vsu.cs.berrielocal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vsu.cs.berrielocal.model.Product
import ru.vsu.cs.berrielocal.model.enums.Category

interface ProductRepository : JpaRepository<Product, Long> {

    @Query(
        nativeQuery = true,
        value = """
            SELECT DISTINCT p.categories FROM products p
            WHERE p.shop_shop_id = :shopId
        """
    )
    fun findAllCategoriesByShopId(shopId: Long): Set<Category>
}