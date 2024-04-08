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
   var orderId: Long? = null,

   var address: String? = null,

    @CreatedDate
   var date: LocalDateTime? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
   var customer: Shop? = null,
) {
    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = false
    )
   var ordersParts: MutableList<OrderPart> = mutableListOf()
}