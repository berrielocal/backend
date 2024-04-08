package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.repository.converter.StringToSetCategoryAttributeConverter

@Entity
@Table(name = "shops")
data class Shop (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val shopId: Long? = null,

    val name: String? = null,

    val email: String? = null,

    val password: String? = null,

    val phoneNumber: String? = null,

    val imageUrl: String? = null,

    val isActive: Boolean = false,

    @Convert(converter = StringToSetCategoryAttributeConverter::class)
    val categories: Set<Category>? = emptySet()
) {
    @OneToMany(
        mappedBy = "customer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val ownComments: MutableList<Comment> = mutableListOf()

    @OneToMany(
        mappedBy = "seller",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val receivedComments: MutableList<Comment> = mutableListOf()

    @OneToMany(
        mappedBy = "customer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val orders: MutableList<Order> = mutableListOf()

    @OneToMany(
        mappedBy = "customer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val cartItems: MutableList<CartItem> = mutableListOf()
}