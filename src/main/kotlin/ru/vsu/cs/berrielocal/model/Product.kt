package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
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
    var productId: Long? = null,

    var name: String? = null,

    var units: String? = null,

    var minSize: Long? = null,

    var maxSize: Long? = null,

    var cost: BigDecimal? = null,

    var imageUrl: String? = null,

    var description: String? = null,

    @Convert(converter = StringToSetCategoryAttributeConverter::class)
    var categories: Set<Category>? = emptySet(),

    @ManyToOne(
        fetch = FetchType.EAGER,
        optional = false
    )
    var shop: Shop? = null,
) {
    @OneToMany(
        mappedBy = "product",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    var orderParts: MutableList<OrderPart> = mutableListOf()
}
