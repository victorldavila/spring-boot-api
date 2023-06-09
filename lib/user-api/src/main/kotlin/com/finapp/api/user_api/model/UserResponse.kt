package com.finapp.api.user_api.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.user_api.role.model.RoleResponse
import com.finapp.api.user_api.token.model.TokenResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserResponse(
    @JsonProperty("id") val id: String?,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("token") val token: TokenResponse? = null,
    @JsonProperty("roles") val roles: List<RoleResponse>? = null
)
