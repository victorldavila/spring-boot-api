package com.finapp.api.products_combination.model

import com.finapp.api.core.validation.*
import jakarta.validation.Valid

data class ProductsCombinationArg(
    @field:Valid
    val productsCombinationParam: ProductsCombinationParam? = null,
    @field:Valid
    val request: ProductsCombinationRequest? = null
)

data class ProductCombinationItemsArg(
    @field:Valid
    val productsCombinationParam: ProductCombinationItemsParam? = null,
    @field:Valid
    val request: ProductCombinationItemRequest? = null
)

data class ProductsCombinationParam(
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class, OnRead::class ], message = "product combination id must be valid")
    val productCombinationId: String?
)

data class ProductCombinationItemsParam(
    @field:ObjectId(groups = [ OnCreate::class, OnDeleteItems::class, OnReadItems::class ], message = "product combination id must be valid")
    val productCombinationId: String?,
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class, OnRead::class ], message = "product combination id must be valid")
    val productCombinationItemId: String? = null
)