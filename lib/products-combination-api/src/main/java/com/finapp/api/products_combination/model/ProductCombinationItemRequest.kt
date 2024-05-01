package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ProductCombinationItemRequest(
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("price") val price: BigDecimal? = null,
    @JsonProperty("quantity") val quantity: Int?,
    @JsonProperty("productId") val productId: String?
)
