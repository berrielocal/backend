package ru.vsu.cs.berrielocal.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ru.vsu.cs.berrielocal.model.enums.OrderPartStatus
import java.time.LocalDateTime

@Entity
@Table(name = "order_parts")
data class OrderPart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderPartId: Long? = null,

    @Enumerated(EnumType.STRING)
    var status: OrderPartStatus? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = true
    )
    var order: Order? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    var product: Product? = null,

    var size: Long? = null,

    var customerId: Long? = null,

    var updatedAt: LocalDateTime? = null,

    var orderedAt: LocalDateTime? = null
)


