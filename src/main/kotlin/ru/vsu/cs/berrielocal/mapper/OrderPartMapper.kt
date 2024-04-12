package ru.vsu.cs.berrielocal.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import ru.vsu.cs.berrielocal.dto.cart.OrderPartMainInfo
import ru.vsu.cs.berrielocal.model.OrderPart

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface OrderPartMapper {

    @Mapping(source = "product.productId", target = "productId")
    fun toMainInfo(orderPart: OrderPart): OrderPartMainInfo
}