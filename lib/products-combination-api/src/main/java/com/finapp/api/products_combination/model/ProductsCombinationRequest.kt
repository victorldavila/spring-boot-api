package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductsCombinationRequest(
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination name can not be blanked")
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: BigDecimal?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination isVariable can not be null")
    @JsonProperty("isVariable") val isVariable: Boolean?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination isActive can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination type can not be blanked")
    @JsonProperty("type") val type: ProductsCombinationType?,
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "Product Combination category can not be blanked")
    @JsonProperty("category") val category: String?,

    @field:Valid
    @JsonProperty("products") val products: List<ProductCombinationItemRequest>? = null,
)
