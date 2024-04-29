package ru.vsu.cs.berrielocal.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vsu.cs.berrielocal.configuration.API_VERSION
import ru.vsu.cs.berrielocal.dto.security.JwtResponse
import ru.vsu.cs.berrielocal.dto.security.UserAuthorizationRequest
import ru.vsu.cs.berrielocal.dto.security.UserIdResponse
import ru.vsu.cs.berrielocal.dto.security.UserRefreshResponse
import ru.vsu.cs.berrielocal.dto.security.UserRegistrationRequest
import ru.vsu.cs.berrielocal.security.JwtTokenProvider
import ru.vsu.cs.berrielocal.service.UserService


@RestController
@RequestMapping(API_VERSION)
@Tag(name = "AuthController", description = "Аутентификация")
class AuthController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/users/login")
    @Operation(summary = "Авторизовать пользователя", description = "Принимает UserAuthDTO")
    fun authorizeUser(@RequestBody request: UserAuthorizationRequest): ResponseEntity<JwtResponse?> {
        val response = userService.authorizeUser(request)

        return if (response != null) ResponseEntity.ok(response) else ResponseEntity.badRequest().build()
    }

    @PostMapping("/users/registration")
    @Operation(summary = "Регистрация пользователя")
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
            .ok(userService.refreshToken(refreshToken))
    }

    @PatchMapping("/users/activate/{activationCode}")
    fun activateAccount(
        @RequestHeader("Authorization") token: String,
        @PathVariable activationCode: String
    ): ResponseEntity<*> {
        val strId = jwtTokenProvider.getCustomClaimValue(token, "id")
        val id = strId.toLong()
        val activated = userService.tryActivateAccount(id, activationCode)

        return if (activated) ResponseEntity.ok().build<Any>() else ResponseEntity.badRequest().build<Any>()
    }

    @GetMapping("/users")
    @Operation(summary = "Найти пользователя по id", description = "Принимает id пользователя")
    fun getUserById(@RequestHeader("Authorization") token: String): ResponseEntity<UserIdResponse> {
        val strId = jwtTokenProvider.getCustomClaimValue(token, "id")
        val id = strId.toLong()
        return ResponseEntity.ok(UserIdResponse(id))
    }
}