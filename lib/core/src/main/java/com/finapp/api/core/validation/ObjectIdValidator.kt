package com.finapp.api.core.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.bson.types.ObjectId


class ObjectIdValidator : ConstraintValidator<com.finapp.api.core.validation.ObjectId, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return ObjectId.isValid(value)
    }
}