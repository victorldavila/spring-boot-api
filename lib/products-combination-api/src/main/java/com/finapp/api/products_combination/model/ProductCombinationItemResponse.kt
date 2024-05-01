package com.finapp.api.products_combination.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.products.model.ProductResponse
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductCombinationItemResponse(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("price") val price: BigDecimal? = null,
    @JsonProperty("quantity") val quantity: Int?,
    @JsonProperty("product") val product: ProductResponse? = null,
)
