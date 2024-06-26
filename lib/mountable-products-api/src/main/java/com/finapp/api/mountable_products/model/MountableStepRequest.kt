package com.finapp.api.mountable_products.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.core.validation.OnUpdateItems
import com.finapp.api.mountable_products.validation.MountableProductType

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class MountableStepRequest(
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "step name can not be blanked")
    @JsonProperty("name") val name: String?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "step minimum can not be null")
    @JsonProperty("minimum") val minimum: Int?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "step minimum can not be null")
    @JsonProperty("isActive") val isActive: Boolean?,
    @field:NotNull(groups = [ OnCreate::class, OnUpdate::class ], message = "step isActive can not be null")
    @JsonProperty("maximum") val maximum: Int?,
    @field:MountableProductType(groups = [ OnCreate::class, OnUpdate::class ])
    @JsonProperty("type") val type: String?,

    @field:Size(min = 1, groups = [ OnCreate::class, OnUpdate::class ], message = "step items must have 1 item at least")
    @field:Valid
    @JsonProperty("items") val items: List<MountableItemRequest>?
)
