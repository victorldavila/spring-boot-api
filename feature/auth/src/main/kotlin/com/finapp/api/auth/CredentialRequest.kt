package com.finapp.api.auth

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CredentialRequest(
    @JsonProperty("username")
    @NotBlank(message = "username can not be blanked")
    val username: String,
    @JsonProperty("password")
    @NotBlank(message = "password can not be blanked")
    val password: String
)
