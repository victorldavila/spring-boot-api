package com.finapp.api.mountable_products.model

import com.finapp.api.core.validation.*
import jakarta.validation.Valid

data class MountableProductArg(
    @field:Valid
    val mountableProductParam: MountableProductParam? = null,
    @field:Valid
    val request: MountableStepRequest? = null
)

data class MountableProductParam(
    @field:ObjectId(groups = [ OnCreate::class, OnRead::class ], message = "product id must be valid")
    val productId: String?,
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class ], message = "mountable step id must be valid")
    val mountableProductId: String? = null,
)
