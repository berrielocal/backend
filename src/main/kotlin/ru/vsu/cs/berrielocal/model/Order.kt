package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null,

    val address: String? = null,

    @CreatedDate
    val date: LocalDateTime? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val customer: Shop? = null,
) {
    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
    val ordersParts: MutableList<OrderPart> = mutableListOf()
}