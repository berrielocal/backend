package ru.vsu.cs.berrielocal.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass
import ru.vsu.cs.berrielocal.validation.validators.ProductArgsValidator

@Constraint(validatedBy = [ProductArgsValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductArgsValidation(
    val message: String = "Некорректное тело запроса/запрос не прошел валидацию",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
