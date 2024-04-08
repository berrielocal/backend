package ru.vsu.cs.berrielocal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
data class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentId: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val customer: Shop? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    val seller: Shop? = null,

    val rate: Double? = null,

    val text: String? = null,

    @CreatedDate
    val createdAt: LocalDateTime? = null
)