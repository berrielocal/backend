package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.vsu.cs.berrielocal.model.enums.Category
import ru.vsu.cs.berrielocal.model.security.Role
import ru.vsu.cs.berrielocal.repository.converter.StringToSetCategoryAttributeConverter


@Entity
@Table(name = "shops")
data class Shop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var shopId: Long? = null,

    var name: String? = null,

    var email: String? = null,

    private var password: String? = null,

    var phoneNumber: String? = null,

    var imageUrl: String? = null,

    var isActive: Boolean = false,

    @Convert(converter = StringToSetCategoryAttributeConverter::class)
    var categories: Set<Category>? = emptySet(),

    @Enumerated(EnumType.STRING)
    var role: Role? = null
) : UserDetails {
    @OneToMany(
        mappedBy = "customer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    var ownComments: MutableList<Comment> = mutableListOf()

    @OneToMany(
        mappedBy = "seller",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    var receivedComments: MutableList<Comment> = mutableListOf()

    @OneToMany(
        mappedBy = "customer",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    var orders: MutableList<Order> = mutableListOf()

    @OneToMany(
        mappedBy = "shop",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    var products: MutableList<Product> = mutableListOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(role as GrantedAuthority)
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }
}