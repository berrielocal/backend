package ru.vsu.cs.berrielocal.service

import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.dto.product.ProductCreateResponse
import ru.vsu.cs.berrielocal.dto.product.ProductListResponse
import ru.vsu.cs.berrielocal.dto.product.ProductModifyRequest
import ru.vsu.cs.berrielocal.dto.product.ProductResponse
import ru.vsu.cs.berrielocal.exception.ProductNotFoundException
import ru.vsu.cs.berrielocal.exception.ShopNotFoundException
import ru.vsu.cs.berrielocal.mapper.ProductMapper
import ru.vsu.cs.berrielocal.model.Product
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.ProductRepository
import ru.vsu.cs.berrielocal.repository.ShopRepository

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val mapper: ProductMapper,
    private val shopRepository: ShopRepository
) {
    fun getProductsByShopId(shopId: Long): ProductListResponse? {
        val products = productRepository.findAllProductsByShopId(shopId)

        val categoriesList = Category.entries.toList()
        val mapCategoriesWithProducts = mutableMapOf<Category, List<ProductResponse>?>()
        categoriesList.forEach { category ->
            val productsFromRepository = products.filter {
                it.categories?.contains(category) ?: false
            }.map (mapper::toProductResponse)
            mapCategoriesWithProducts[category] = productsFromRepository.takeIf { it.isNotEmpty() }
        }

        return ProductListResponse(shopId = shopId, products = mapCategoriesWithProducts)
    }

    fun getById(productId: Long): ProductResponse {
        return mapper.toProductResponse(getByIdEntity(productId))
    }

    fun getByIdEntity(productId: Long): Product {
        val productInDb = productRepository.findById(productId)

        if (productInDb.isEmpty) {
            throw ProductNotFoundException("Not found product by productId:$productId")
        }

        return productInDb.get()
    }

    fun create(request: ProductModifyRequest, shopId: Long): ProductCreateResponse? {
        val productToSave = mapper.toProduct(null, request)

        val shop = shopRepository.findById(shopId).getOrNull()

        return if (shop != null) {
            val productInDb = productRepository.save(
                productToSave.apply { this.shop = shop }
            )

            ProductCreateResponse(productInDb.productId!!)
        } else {
            throw ShopNotFoundException("Not found shop by shopId:${shopId}")
        }
    }

    fun updateById(productId: Long, request: ProductModifyRequest, shopId: Long) {
        val productToSave = mapper.toProduct(productId.toString(), request)
        val shop = shopRepository.findById(shopId).getOrNull()

        if (shop != null) {
            val productInDb = productRepository.findById(productId).takeIf { it.isPresent }?.get()
                ?: throw ProductNotFoundException("Not found product by productId:$productId")

            productInDb.apply {
                this.maxSize = productToSave.maxSize ?: this.maxSize
                this.minSize = productToSave.minSize ?: this.minSize
                this.cost = productToSave.cost ?: this.cost
                this.name = productToSave.name ?: this.name
                this.units = productToSave.units ?: this.units
            }

            productRepository.save(
                productInDb
            )
        } else {
            throw ShopNotFoundException("Not found shop by shopId:${shopId}")
        }
    }

    fun deleteById(productId: Long) {
        productRepository.deleteById(productId)
    }

    fun findCategoriesByShopId(shopId: Long?) = shopId?.let { productRepository.findAllCategoriesByShopId(shopId) }

}
