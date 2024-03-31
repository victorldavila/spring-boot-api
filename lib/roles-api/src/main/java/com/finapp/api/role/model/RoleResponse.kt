package com.finapp.api.role.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RoleResponse(
    @JsonProperty("type") val type: String,
    @JsonProperty("name") val name: String,
)
