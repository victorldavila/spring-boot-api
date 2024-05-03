package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.ObjectId
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductCombinationItemRequest(
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination item name can not be blanked")
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("price") val price: BigDecimal? = null,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination item isVariable can not be null")
    @JsonProperty("isVariable") val isVariable: Boolean?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "sProduct Combination item quantity can not be null")
    @JsonProperty("quantity") val quantity: Int?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination item isActive can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @field:ObjectId(groups = [ OnCreate::class, OnUpdate::class ], message = "product id must be valid")
    @JsonProperty("productId") val productId: String?
)
