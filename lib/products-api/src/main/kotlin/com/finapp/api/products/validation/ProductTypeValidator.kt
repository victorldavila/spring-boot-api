package com.finapp.api.products.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ProductTypeValidator : ConstraintValidator<ProductType, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return !value.isNullOrEmpty() && com.finapp.api.products.model.ProductType.getAllProductType().contains(value)
    }
}