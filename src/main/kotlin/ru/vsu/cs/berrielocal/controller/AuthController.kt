package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.security.JwtResponse
import ru.vsu.cs.berrielocal.dto.security.UserAuthorizationRequest
import ru.vsu.cs.berrielocal.dto.security.UserRefreshResponse
import ru.vsu.cs.berrielocal.dto.security.UserRegistrationRequest
import ru.vsu.cs.berrielocal.service.UserService


@RestController
@RequestMapping(API_VERSION)
@Tag(name = "AuthController", description = "Аутентификация")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/users/login")
    @Operation(summary = "Авторизовать пользователя", description = "Принимает UserAuthDTO")
    @Throws(
        AuthenticationException::class
    )
    fun authorizeUser(@RequestBody request: UserAuthorizationRequest): ResponseEntity<JwtResponse?> {
        val response = userService.authorizeUser(request)

        return if (response != null) ResponseEntity.ok(response) else ResponseEntity.badRequest().build()
    }

    @PostMapping("/users/registration")
    @Operation(summary = "Регистрация пользователя")
    @Throws(
        AuthenticationException::class
    )
    fun registrationUser(
        @RequestBody request: UserRegistrationRequest
    ): ResponseEntity<JwtResponse?> {
        try {
            userService.registrationUser(request)
        } catch (e: Exception) {
            return ResponseEntity(null, HttpStatus.CONFLICT)
        }

        val response = userService.authorizeUser(
            UserAuthorizationRequest(
                email = request.email,
                password = request.password
            )
        )
        return if (response != null) ResponseEntity.ok(response) else ResponseEntity.badRequest().build()
    }

    @PostMapping("/users/refresh")
    fun refreshToken(@RequestBody refreshToken: String?): ResponseEntity<UserRefreshResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.refreshToken(refreshToken))
    }
}