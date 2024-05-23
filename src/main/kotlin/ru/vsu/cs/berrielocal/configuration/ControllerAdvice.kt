package ru.vsu.cs.berrielocal.configuration

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vsu.cs.berrielocal.exception.ChangeOrderPartStatusException
import ru.vsu.cs.berrielocal.exception.CommentCreateException
import ru.vsu.cs.berrielocal.exception.OrderCreateException
import ru.vsu.cs.berrielocal.exception.OrderPartNotFoundException
import ru.vsu.cs.berrielocal.exception.ProductAddToCartException
import ru.vsu.cs.berrielocal.exception.ProductNotFoundException
import ru.vsu.cs.berrielocal.exception.ShopNotFoundException
import ru.vsu.cs.berrielocal.exception.UnauthorizedException

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(
        value = [
            ShopNotFoundException::class,
            ProductNotFoundException::class,
            CommentCreateException::class,
            ProductAddToCartException::class,
            OrderPartNotFoundException::class,
            OrderCreateException::class,
            ChangeOrderPartStatusException::class,
        ]
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleExceptionWithNotFoundReason(exception: RuntimeException): String? {
        return exception.message
    }

    @ExceptionHandler(
        value = [
            UnauthorizedException::class
        ]
    )
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException() { }
}