package ru.vsu.cs.berrielocal.service

import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
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
import java.util.*


@Service
class UserService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val shopRepository: ShopRepository,
    private val mailSender: JavaMailSender
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
            val code = Random().nextInt(10000, 99999).toString()
            shopRepository.save(
                Shop(
                    role = Role.USER,
                    email = request.email,
                    password = encoder.encode(request.password),
                    name = request.name,
                    phoneNumber = request.phoneNumber,
                    imageUrl = request.imageUrl,
                    activationCode = code
                )
            )
            sendEmailByRegistration(request.email, code)
        }
    }

    fun refreshToken(refreshToken: String?): UserRefreshResponse? {
        val email = jwtTokenProvider.getUsernameFromJwt(refreshToken!!)
        val dbUser = shopRepository.findByEmail(email)
        return if (dbUser != null) createTokensForUser(dbUser) else null
    }

    fun tryActivateAccount(userId: Long, activationCode: String): Boolean {
        val user = shopRepository.findById(userId)

        if (user.isEmpty) {
            return false
        }

        val entity = user.get()

        if (entity.activationCode != activationCode) {
            return false
        }

        shopRepository.save(entity.apply {
            this.isActive = true
        })

        return true
    }

    private fun createTokensForUser(user: Shop): UserRefreshResponse {
        return UserRefreshResponse(
            jwtTokenProvider.generateAccessToken(user),
            jwtTokenProvider.generateRefreshToken(user)
        )
    }

    private fun getByEmail(email: String) =
        shopRepository.findByEmail(email)

    private fun sendEmailByRegistration(subject: String, activationCode: String) {
        val message = SimpleMailMessage()
        message.from = "berrielocal@gmail.com"
        message.setTo(subject)
        message.text = """
            Благодарим Вас за регистрацию! Для подтверждения почты введите код в приложении -
            $activationCode
        """.trimIndent()

        mailSender.send(message)
    }
}