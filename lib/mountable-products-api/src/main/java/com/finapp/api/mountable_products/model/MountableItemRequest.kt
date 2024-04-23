package com.finapp.api.mountable_products.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Reduce.Variable
import java.math.BigDecimal

data class MountableItemRequest(
    @field:NotNull(groups = [ OnUpdate::class ], message = "step item index can not be null")
    @JsonProperty("index") val index: Int,
    @field:NotBlank(groups = [ OnCreate::class ], message = "step item name can not be blanked")
    @JsonProperty("name") val name: String?,
    @JsonProperty("isVariable") val isVariable: Boolean?,
    @JsonProperty("price") val price: BigDecimal?,
    @field:NotNull(groups = [ OnCreate::class ], message = "step isActive can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @field:NotNull(groups = [ OnCreate::class ], message = "step item maximum quantity can not be null")
    @JsonProperty("maximumQuantity") val maximumQuantity: Int?,
    @field:NotNull(groups = [ OnCreate::class ], message = "step item subtraction quantity can not be null")
    @JsonProperty("subtractionQuantity") val subtractionQuantity: Int?,
    @field:NotBlank(groups = [ OnCreate::class ], message = "step item measure can not be blanked")
    @JsonProperty("measure") val measure: String?
)
