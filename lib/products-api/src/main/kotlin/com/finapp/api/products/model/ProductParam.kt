package com.finapp.api.products.model

import com.finapp.api.core.validation.OnDelete
import com.finapp.api.core.validation.OnRead
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.core.validation.ValidObjectId
import jakarta.validation.Valid

data class ProductArg(
    @field:Valid
    val productParam: ProductParam? = null,
    @field:Valid
    val request: ProductRequest? = null
)

data class ProductParam(
    @field:ValidObjectId(groups = [ OnUpdate::class, OnDelete::class, OnRead::class ], message = "product id must be valid")
    val productId: String?
)
