package ru.vsu.cs.berrielocal.service

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.vsu.cs.berrielocal.dto.security.JwtResponse
import ru.vsu.cs.berrielocal.dto.security.UserAuthorizationRequest
import ru.vsu.cs.berrielocal.dto.security.UserRefreshResponse
import ru.vsu.cs.berrielocal.dto.security.UserRegistrationRequest
import ru.vsu.cs.berrielocal.model.Shop
import ru.vsu.cs.berrielocal.model.security.Role
import ru.vsu.cs.berrielocal.repository.ShopRepository
import ru.vsu.cs.berrielocal.security.JwtTokenProvider


@Service
class UserService(
    val authenticationManager: AuthenticationManager,
    val jwtTokenProvider: JwtTokenProvider,
    val shopRepository: ShopRepository
) {

    @Transactional
    @Throws(AuthenticationException::class)
    fun authorizeUser(request: UserAuthorizationRequest): JwtResponse? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email, request.password
            )
        )
        val user = getByEmail(request.email)
        return if (user != null) {
            JwtResponse(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user))
        } else null
    }

    @Transactional
    fun registrationUser(request: UserRegistrationRequest) {
        if (shopRepository.findByEmail(request.email) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "HTTP Status will be NOT FOUND (CODE 404)\n")
        } else {
            val encoder = BCryptPasswordEncoder()
            shopRepository.save(
                Shop(
                    role = Role.USER,
                    email = request.email,
                    password = encoder.encode(request.password)
                )
            )
        }
    }

    fun refreshToken(refreshToken: String?): UserRefreshResponse? {
        val email = jwtTokenProvider.getUsernameFromJwt(refreshToken!!)
        val dbUser = shopRepository.findByEmail(email)
        return if (dbUser != null) createTokensForUser(dbUser) else null
    }

    private fun createTokensForUser(user: Shop): UserRefreshResponse {
        return UserRefreshResponse(jwtTokenProvider.generateAccessToken(user), jwtTokenProvider.generateRefreshToken(user))
    }

    private fun getByEmail(email: String) =
        shopRepository.findByEmail(email)
}