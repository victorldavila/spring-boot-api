package com.finapp.mountable_products.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MountableProductTypeValidator : ConstraintValidator<MountableProductType, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return !value.isNullOrEmpty() && com.finapp.mountable_products.model.MountableProductType.getAllMountableProductType().contains(value)
    }
}