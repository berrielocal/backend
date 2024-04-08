package ru.vsu.cs.berrielocal.model.security

import org.springframework.security.core.GrantedAuthority

enum class Role(val value: String) : GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    override fun getAuthority(): String {
        return value
    }
}
