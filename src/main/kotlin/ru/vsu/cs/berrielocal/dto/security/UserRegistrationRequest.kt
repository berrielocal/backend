package ru.vsu.cs.berrielocal.dto.security

import ru.vsu.cs.berrielocal.model.enums.Category

data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val phoneNumber: String? = null,
    val imageUrl: String? = null,
    val categories: Set<Category>? = null,
    var description: String? = null,
)