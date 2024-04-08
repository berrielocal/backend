package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.converter.StringToSetCategoryAttributeConverter
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: String? = null,

    val name: String? = null,

    val units: String? = null,

    val minSize: Double? = null,

    val maxSize: Double? = null,

    val cost: BigDecimal? = null,

    val imageUrl: String? = null,

    @Convert(converter = StringToSetCategoryAttributeConverter::class)
    val categories: Set<Category>? = emptySet()
) {
    @OneToMany(
        mappedBy = "product",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val orderParts: MutableList<OrderPart> = mutableListOf()

    @OneToMany(
        mappedBy = "product",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val cartItems: MutableList<CartItem> = mutableListOf()
}
