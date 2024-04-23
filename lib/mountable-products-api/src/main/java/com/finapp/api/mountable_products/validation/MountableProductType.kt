package com.finapp.api.mountable_products.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [MountableProductTypeValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MountableProductType(
    val message: String = "Mountable Product type is not Valid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)