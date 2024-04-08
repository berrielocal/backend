package ru.vsu.cs.berrielocal.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartItemId: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val product: Product? = null,

    val count: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val customer: Shop? = null,
)