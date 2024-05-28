package ru.vsu.cs.berrielocal.service

import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.shop.ShopAllInfoResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopListResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopMainInfo
import ru.vsu.cs.berrielocal.dto.shop.ShopUpdateRequest
import ru.vsu.cs.berrielocal.exception.ShopNotFoundException
import ru.vsu.cs.berrielocal.mapper.ShopMapper
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.CommentRepository
import ru.vsu.cs.berrielocal.repository.ShopRepository

@Service
class ShopService(
    private val shopRepository: ShopRepository,
    private val mapper: ShopMapper,
    private val matchService: MatchService,
    private val productService: ProductService,
    private val commentRepository: CommentRepository
) {
    fun getShopsList(categories: List<Category>?): ShopListResponse {
        val shopsFromRepository = shopRepository.findAll()

        val categoriesList = categories.takeIf { it != null } ?: Category.entries.toList()
        val mapCategoriesWithShops = mutableMapOf<Category, List<ShopMainInfo>?>()
        categoriesList.forEach { category ->
            val shopsWithCurrentCategory = shopsFromRepository.filterNotNull().filter {
                productService.findCategoriesByShopId(it.shopId)?.contains(category) ?: false
            }.map { mapper.toMainInfo(it, commentRepository.findAverageRateBySellerId(it.shopId) ?: 0.0) }
            mapCategoriesWithShops[category] = shopsWithCurrentCategory.takeIf { it.isNotEmpty() }
        }

        return ShopListResponse(shops = mapCategoriesWithShops)
    }

    fun getByShopId(shopId: Long, customerId: Long?): ShopAllInfoResponse? {
        val matchLevel = customerId?.let {
            matchService.evaluateMatchLevelBetweenCustomerAndShop(
                customerId = customerId,
                shopId = shopId
            )
        } ?: 0.0

        val shopFromDb = shopRepository.findById(shopId)

        return if (shopFromDb.isEmpty.not()) {
            val shop = shopFromDb.get()
            val mapped = mapper.toAllInfo(shop, matchLevel, commentRepository.findAverageRateBySellerId(shop.shopId))

            mapped.categories = shop.categories?.map(Category::type)?.toSet()

            mapped
        } else {
            throw ShopNotFoundException("Not found any shop by shopId: $shopId")
        }
    }

    fun getById(shopId: Long?) = shopId?.let { shopRepository.findById(shopId).getOrNull() }


    fun updateById(shopId: Long, shopFromRequest: ShopUpdateRequest) {
        val shopFromDbByIdOpt = shopRepository.findById(shopId)

        if (shopFromDbByIdOpt.isEmpty) {
            throw ShopNotFoundException("Not found any shop by shopId: $shopId")
        }
        val shopFromDb = shopFromDbByIdOpt.get()

        shopFromDb.apply {
            this.imageUrl = shopFromRequest.imageUrl ?: imageUrl
            this.email = shopFromRequest.email ?: email
            this.name = shopFromRequest.name ?: name
            this.phoneNumber = shopFromRequest.phoneNumber ?: phoneNumber
            this.categories = shopFromRequest.categories ?: categories
        }

        shopRepository.save(shopFromDb)
    }

    fun deleteById(shopId: Long) {
        shopRepository.deleteById(shopId)
    }
}
