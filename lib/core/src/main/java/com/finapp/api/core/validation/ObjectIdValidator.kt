package com.finapp.api.core.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.bson.types.ObjectId
import org.springframework.beans.BeanWrapperImpl


class ObjectIdValidator : ConstraintValidator<ValidObjectId, String?> {
    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return ObjectId.isValid(value)
    }
}