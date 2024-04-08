package ru.vsu.cs.berrielocal.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryId: String? = null,

    val name: String? = null
) {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "categories_shops",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "shop_id")]
    )
    val shops: MutableList<Shop> = mutableListOf()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "categories_products",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableList<Product> = mutableListOf()
}
