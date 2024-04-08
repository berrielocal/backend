package ru.vsu.cs.berrielocal.service.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.vsu.cs.berrielocal.repository.ShopRepository


@Service
class UserDetailsServiceImpl(private val shopRepository: ShopRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = shopRepository.findByEmail(username)
        return if (user != null) {
            val authority = user.role?.authority
            val simpleGrantedAuthorities: MutableList<SimpleGrantedAuthority> = ArrayList()

            simpleGrantedAuthorities.add(SimpleGrantedAuthority(authority))

            User(user.username, user.password, simpleGrantedAuthorities)
        } else throw UsernameNotFoundException("Invalid username")
    }
}