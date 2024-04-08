package ru.vsu.cs.berrielocal.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import kotlin.collections.ArrayList
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import ru.vsu.cs.berrielocal.model.Shop
import java.util.*


@Component
class JwtTokenProvider(
    @Value("\${jwt.secret.refresh}")
    val refreshSecretKey: String,
    @Value("\${jwt.secret.access}")
    val accessSecretKey: String,
    @Value("\${jwt.expiration.access}")
    val accessValidity: Long,
    @Value("\${jwt.expiration.refresh}")
    val refreshValidity: Long
) {

    fun generateAccessToken(user: Shop): String {
        val algorithm = Algorithm.HMAC256(accessSecretKey)
        return JWT.create()
            .withSubject(user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + accessValidity))
            .withClaim("id", user.shopId)
            .withClaim("authority", user.role?.authority)
            .sign(algorithm)
    }

    fun generateRefreshToken(user: Shop): String {
        val algorithm = Algorithm.HMAC256(refreshSecretKey)
        return JWT.create()
            .withSubject(user.name)
            .withExpiresAt(Date(System.currentTimeMillis() + refreshValidity))
            .sign(algorithm)
    }

    fun getAuthTokenFromJwt(jwtToken: String): UsernamePasswordAuthenticationToken {
        val decodedJWT = decodeAccessJWT(jwtToken)
        val username = decodedJWT.subject
        val authority = decodedJWT.getClaim("authority").asString()
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(authority))
        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }

    fun getUsernameFromJwt(jwtToken: String): String {
        val decodedJWT = decodeRefreshJWT(jwtToken)
        return decodedJWT.subject
    }

    fun getCustomClaimValue(jwtToken: String, claimName: String?): String {
        val claims = decodeAccessJWT(jwtToken).claims
        return claims["id"].toString()
    }

    private fun decodeAccessJWT(jwtToken: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(accessSecretKey)
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(jwtToken)
    }

    private fun decodeRefreshJWT(jwtToken: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(refreshSecretKey)
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(jwtToken)
    }
}