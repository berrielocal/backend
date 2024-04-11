package ru.vsu.cs.berrielocal.service

import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.exception.ShopNotFoundException
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.ProductRepository
import ru.vsu.cs.berrielocal.repository.ShopRepository
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class MatchService(
    private val shopRepository: ShopRepository,
    private val productRepository: ProductRepository
) {

    fun evaluateMatchLevelBetweenCustomerAndShop(customerId: Long, shopId: Long): Double {
        val customerDb = shopRepository.findById(customerId)

        val shopCategories = productRepository.findAllCategoriesByShopId(shopId)

        if (customerDb.isEmpty || shopCategories.isEmpty())
            throw ShopNotFoundException("Entity not found in db by id")

        val customerEntity = customerDb.get()
        val customerCategories = customerEntity.categories ?: emptySet()


        val intersection = evaluateIntersection(customerCategories, shopCategories)

        val matchLevel = intersection.count() * 1.0 / customerCategories.count().coerceAtMost(shopCategories.count())

        return (matchLevel * 100).round()
    }

    private fun evaluateIntersection(
        customerCategories: Set<Category>,
        shopCategories: Set<Category>
    ): Set<Category> {
        val intersection = mutableSetOf<Category>()

        val minCategoriesSet = if (customerCategories.count() <= shopCategories.count()) customerCategories
        else shopCategories

        val maxCategoriesSet = if (customerCategories.count() <= shopCategories.count()) shopCategories
        else customerCategories

        minCategoriesSet.forEach {
            if (maxCategoriesSet.contains(it)) {
                intersection.add(it)
            }
        }

        return intersection
    }

    private fun Double.round(): Double {
        return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}