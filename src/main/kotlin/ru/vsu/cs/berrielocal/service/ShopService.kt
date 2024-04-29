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
import ru.vsu.cs.berrielocal.repository.ShopRepository

@Service
class ShopService(
    private val shopRepository: ShopRepository,
    private val mapper: ShopMapper,
    private val matchService: MatchService,
    private val productService: ProductService
) {
    fun getShopsList(categories: List<Category>?): ShopListResponse {
        val shopsFromRepository = shopRepository.findAll()

        val categoriesList = categories.takeIf { it != null } ?: Category.entries.toList()
        val mapCategoriesWithShops = mutableMapOf<Category, List<ShopMainInfo>?>()
        categoriesList.forEach { category ->
            val shopsWithCurrentCategory = shopsFromRepository.filter {
                productService.findCategoriesByShopId(it.shopId)?.contains(category) ?: false
            }.map (mapper::toMainInfo)
            mapCategoriesWithShops[category] = shopsWithCurrentCategory.takeIf { it.isNotEmpty() }
        }

        return ShopListResponse(shops = mapCategoriesWithShops)
    }

    fun getByShopId(shopId: Long, customerId: Long): ShopAllInfoResponse? {
        val matchLevel = matchService.evaluateMatchLevelBetweenCustomerAndShop(
            customerId = customerId,
            shopId = shopId
        )

        val shopFromDb = shopRepository.findById(shopId)

        return if (shopFromDb.isEmpty.not()) {
            mapper.toAllInfo(shopFromDb.get(), matchLevel)
        } else {
            throw ShopNotFoundException("Not found any shop by shopId: $shopId")
        }
    }

    fun getById(shopId: Long?) = shopId?.let { shopRepository.findById(shopId).getOrNull() }


    fun updateById(shopId: Long, shopFromRequest: ShopUpdateRequest) {
        val shopModel = mapper.toModel(
            shopId = shopId.toString(),
            updateRequest = shopFromRequest
        )

        shopRepository.save(shopModel)
    }

}
