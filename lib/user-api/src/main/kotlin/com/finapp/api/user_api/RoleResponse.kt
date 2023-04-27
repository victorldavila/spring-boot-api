package com.finapp.api.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class RoleResponse(
    @JsonProperty("type") val type: String,
    @JsonProperty("name") val name: String,
)
