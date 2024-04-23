package com.finapp.api.products.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.products.validation.ProductType
import com.finapp.api.mountable_products.model.MountableStepRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductRequest (
    @field:NotBlank(groups = [ OnCreate::class ], message = "product name can not be blanked")
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: BigDecimal?,
    @field:NotNull(groups = [ OnCreate::class ], message = "isActive can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @JsonProperty("isVariable") val isVariable: Boolean?,
    @field:ProductType(groups = [ OnCreate::class ])
    @JsonProperty("type") val type: String?,
    @field:NotBlank(groups = [ OnCreate::class ], message = "product category can not be blanked")
    @JsonProperty("category") val category: String?,

    @field:Valid
    @JsonProperty("mountableSteps") val steps: List<MountableStepRequest>?
)