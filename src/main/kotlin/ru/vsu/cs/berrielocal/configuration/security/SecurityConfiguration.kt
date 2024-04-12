package ru.vsu.cs.berrielocal.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.vsu.cs.berrielocal.model.security.Role
import ru.vsu.cs.berrielocal.security.JwtTokenFilter


//@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val jwtTokenFilter: JwtTokenFilter
) {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeHttpRequests().requestMatchers("/api/v1/users/login", "/api/v1/users/registration", "/api/v1/users/refresh", "/api/v1/users/activate/**")
            .permitAll();
        http.authorizeHttpRequests().requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
            .permitAll()
        http.authorizeHttpRequests().requestMatchers("/api/v1/product/**").permitAll()
        http.authorizeHttpRequests().requestMatchers("/api/v1/cart/**").permitAll()
        http.authorizeHttpRequests().requestMatchers("/api/v1/comment/shop/**").permitAll()
        http.authorizeHttpRequests().requestMatchers("/api/v1/comment/**").hasAnyRole(Role.USER.toString())
        http.authorizeHttpRequests().requestMatchers("/api/v1/shop/**").permitAll()
        http.authorizeHttpRequests().requestMatchers("/api/v1/order/**").permitAll()
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}