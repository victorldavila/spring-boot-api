package com.finapp.api.user_api

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    @JsonProperty("id") val id: String?,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("token") val token: TokenResponse?,
    @JsonProperty("roles") val roles: List<RoleResponse>
)
