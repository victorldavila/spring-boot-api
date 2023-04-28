package com.finapp.api.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class SignOutRequest(
    @JsonProperty("access_token")
    @field:NotBlank(message = "access token can not be blanked")
    val accessToken: String
)