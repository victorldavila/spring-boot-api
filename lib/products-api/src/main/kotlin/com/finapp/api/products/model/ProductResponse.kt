package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import java.math.BigDecimal

data class ProductResponse(
    @JsonProperty("id") val id: ObjectId? = null,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: BigDecimal?,
    @JsonProperty("isActive") val isActive: Boolean,
    @JsonProperty("isVariable") val isVariable: Boolean,
    @JsonProperty("type") val type: String,
    @JsonProperty("category") val category: String,

    @JsonProperty("mountableSteps") val steps: List<MountableStepResponse>?
)
