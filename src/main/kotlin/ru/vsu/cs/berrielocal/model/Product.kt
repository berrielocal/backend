package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
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

    val cost: BigDecimal? = null,

    val imageUrl: String? = null
) {
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    val categories: List<Category> = emptyList()

    @OneToMany(
        mappedBy = "product",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val orderParts: MutableList<OrderPart> = mutableListOf()
}
