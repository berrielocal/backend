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

@Entity
@Table(name = "order_parts")
data class OrderPart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderPartId: Long? = null,

    @Enumerated(EnumType.STRING)
    val status: OrderPartStatus? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val order: Order? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val product: Product? = null,
)


