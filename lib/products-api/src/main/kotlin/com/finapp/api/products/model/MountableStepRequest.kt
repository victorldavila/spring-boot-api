package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MountableStepRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("minimum") val minimum: Int,
    @JsonProperty("maximum") val maximum: Int,
    @JsonProperty("type") val type: String,
    @JsonProperty("items") val items: List<MountableItemRequest>
)
