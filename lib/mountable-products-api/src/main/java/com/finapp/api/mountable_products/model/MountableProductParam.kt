package com.finapp.api.mountable_products.model

import com.finapp.api.core.validation.*
import jakarta.validation.Valid

data class MountableStepArg(
    @field:Valid
    val mountableStepParam: MountableStepParam? = null,
    @field:Valid
    val request: MountableStepRequest? = null
)

data class MountableStepParam(
    @field:ObjectId(groups = [ OnCreate::class, OnRead::class, OnDeleteItems::class ], message = "product id must be valid")
    val productId: String?,
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class ], message = "mountable step id must be valid")
    val mountableStepId: String? = null,
)

data class MountableItemArg(
    @field:Valid
    val mountableItemParam: MountableItemParam? = null,
    @field:Valid
    val request: MountableItemRequest? = null
)

data class MountableItemParam(
    @field:ObjectId(groups = [ OnCreate::class, OnRead::class, OnDeleteItems::class ], message = "product id must be valid")
    val mountableStepId: String?,
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class ], message = "mountable step id must be valid")
    val mountableItemId: String? = null,
)
