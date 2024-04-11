package ru.vsu.cs.berrielocal.service

import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.shop.ShopAllInfoResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopListResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopUpdateRequest
import ru.vsu.cs.berrielocal.exception.ShopNotFoundException
import ru.vsu.cs.berrielocal.mapper.ShopMapper
import ru.vsu.cs.berrielocal.repository.ShopRepository

@Service
class ShopService(
    private val shopRepository: ShopRepository,
    private val mapper: ShopMapper,
    private val matchService: MatchService
) {
    fun getShopsList(): ShopListResponse {
        val shopsFromRepository = shopRepository.findAll()

        val mainInfoShopsList = shopsFromRepository.map(mapper::toMainInfo)

        return ShopListResponse(shops = mainInfoShopsList)
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

    fun getById(shopId: Long) = shopRepository.findById(shopId).getOrNull()


    fun updateById(shopId: Long, shopFromRequest: ShopUpdateRequest) {
        val shopModel = mapper.toModel(
            shopId = shopId.toString(),
            updateRequest = shopFromRequest
        )

        shopRepository.save(shopModel)
    }

}
