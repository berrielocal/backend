package ru.vsu.cs.berrielocal.mapper

import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import ru.vsu.cs.berrielocal.dto.shop.ShopAllInfoResponse
import ru.vsu.cs.berrielocal.dto.shop.ShopMainInfo
import ru.vsu.cs.berrielocal.dto.shop.ShopUpdateRequest
import ru.vsu.cs.berrielocal.model.Shop

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ShopMapper {

    fun toMainInfo(source: Shop, matchLevel: Double): ShopMainInfo

    fun toAllInfo(source: Shop, matchLevel: Double): ShopAllInfoResponse

    fun toModel(shopId: String, updateRequest: ShopUpdateRequest): Shop
}