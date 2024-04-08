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
   var cartItemId: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
   var product: Product? = null,

   var count: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
   var customer: Shop? = null,
)