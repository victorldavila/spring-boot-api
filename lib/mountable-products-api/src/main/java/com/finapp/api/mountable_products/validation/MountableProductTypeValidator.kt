package com.finapp.api.mountable_products.validation

import com.finapp.api.mountable_products.model.MountableStepType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class MountableProductTypeValidator : ConstraintValidator<MountableProductType, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return !value.isNullOrEmpty() && MountableStepType.getAllMountableProductType().contains(value)
    }
}