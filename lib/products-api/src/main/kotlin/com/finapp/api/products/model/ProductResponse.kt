package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.mountable_products.model.MountableStepResponse
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductResponse(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("isActive") val isActive: Boolean,
    @JsonProperty("type") val type: String,
    @JsonProperty("category") val category: String,

    @JsonProperty("mountableSteps") val steps: List<MountableStepResponse>? = null
)
