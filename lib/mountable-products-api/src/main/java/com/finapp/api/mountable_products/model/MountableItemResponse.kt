package com.finapp.api.mountable_products.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MountableItemResponse(
    @JsonProperty("id") val id: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("maximumQuantity") val maximumQuantity: Int,
    @JsonProperty("subtractionQuantity") val subtractionQuantity: Int,
    @JsonProperty("measure") val measure: String
)
