package com.finapp.mountable_products.model

import com.finapp.api.core.validation.OnDelete
import com.finapp.api.core.validation.OnRead
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.core.validation.ObjectId
import jakarta.validation.Valid

data class MountableProductArg(
    @field:Valid
    val mountableProductParam: MountableProductParam? = null,
    @field:Valid
    val request: MountableStepRequest? = null
)

data class MountableProductParam(
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class, OnRead::class ], message = "mountable step id must be valid")
    val mountableProductId: String?
)
