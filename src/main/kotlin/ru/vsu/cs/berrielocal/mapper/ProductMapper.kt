package ru.vsu.cs.berrielocal.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import ru.vsu.cs.berrielocal.dto.product.ProductModifyRequest
import ru.vsu.cs.berrielocal.dto.product.ProductResponse
import ru.vsu.cs.berrielocal.model.Product

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProductMapper {

    @Mapping(source = "shop.shopId", target = "shopId")
    fun toProductResponse(product: Product) : ProductResponse

    fun toProduct(productId: String?, product: ProductModifyRequest): Product
}
