package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ProductsCombinationRequest(
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("isActive") val isActive: Boolean?,
    @JsonProperty("type") val type: ProductsCombinationType?,
    @JsonProperty("category") val category: String?,

    @JsonProperty("products") val products: List<ProductCombinationItemRequest>? = null,
)
