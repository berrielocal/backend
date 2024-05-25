package ru.vsu.cs.berrielocal.validation.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.vsu.cs.berrielocal.dto.product.ProductModifyRequest
import ru.vsu.cs.berrielocal.validation.ProductArgsValidation

class ProductArgsValidator
    : ConstraintValidator<ProductArgsValidation, ProductModifyRequest> {
    override fun isValid(value: ProductModifyRequest, context: ConstraintValidatorContext?): Boolean {
        val isMaxMoreThanMin = if (value.maxSize != null && value.minSize != null) {
            value.maxSize >= value.minSize
        } else false
        val isCostGreaterZero = value.cost?.takeIf { it > 0 } != null

        return isCostGreaterZero && isMaxMoreThanMin
    }
}