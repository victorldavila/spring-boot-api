package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductRequest (
    @field:NotBlank(groups = [ OnCreate::class ], message = "product name can not be blanked")
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: BigDecimal?,
    @field:NotNull(groups = [ OnCreate::class ], message = "isActive can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @field:NotNull(groups = [ OnCreate::class ], message = "isVariable can not be null")
    @JsonProperty("isVariable") val isVariable: Boolean?,
    @field:NotBlank(groups = [ OnCreate::class ], message = "product type can not be blanked")
    @JsonProperty("type") val type: String?,
    @field:NotBlank(groups = [ OnCreate::class ], message = "product category can not be blanked")
    @JsonProperty("category") val category: String?,

    @JsonProperty("mountableSteps") val steps: List<MountableStepRequest>?
)