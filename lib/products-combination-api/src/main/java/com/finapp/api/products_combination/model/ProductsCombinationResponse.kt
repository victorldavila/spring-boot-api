package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductsCombinationResponse(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("isActive") val isActive: Boolean,
    @JsonProperty("type") val type: ProductsCombinationType,
    @JsonProperty("category") val category: String,

    @JsonProperty("items") val products: List<ProductCombinationItemResponse>? = null,
)
