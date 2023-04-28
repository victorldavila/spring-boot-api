package com.finapp.api.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class TokenRequest(
    @JsonProperty("token")
    @field:NotBlank(message = "token can not be blanked")
    val token: String
)