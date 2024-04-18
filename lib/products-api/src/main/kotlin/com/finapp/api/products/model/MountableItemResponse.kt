package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class MountableItemResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("maximumQuantity") val maximumQuantity: Int,
    @JsonProperty("subtractionQuantity") val subtractionQuantity: Int,
    @JsonProperty("measure") val measure: String
)
