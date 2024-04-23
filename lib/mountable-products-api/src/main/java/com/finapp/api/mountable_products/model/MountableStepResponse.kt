package com.finapp.api.mountable_products.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.mountable_products.model.MountableItemResponse

data class MountableStepResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("minimum") val minimum: Int,
    @JsonProperty("maximum") val maximum: Int,
    @JsonProperty("type") val type: String,
    @JsonProperty("items") val items: List<MountableItemResponse>
)
