package ru.vsu.cs.berrielocal.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var commentId: Long? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    var customer: Shop? = null,

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    var seller: Shop? = null,

    var rate: Double? = null,

    var text: String? = null,

    @CreatedDate
    var createdAt: LocalDateTime? = null
)