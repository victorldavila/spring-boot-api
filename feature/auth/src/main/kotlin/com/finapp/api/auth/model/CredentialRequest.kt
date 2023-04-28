package com.finapp.api.auth.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.auth.validation.OnSignIn
import com.finapp.api.auth.validation.OnSignUp
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CredentialRequest(
    @JsonProperty("username")
    @field:NotBlank(message = "username can not be blanked")
    val username: String,
    @JsonProperty("password")
    @field:NotBlank(groups = [ OnSignIn::class ], message = "password can not be blanked")
    @field:Pattern(groups = [ OnSignUp::class ], regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "invalid password")
    val password: String
)
