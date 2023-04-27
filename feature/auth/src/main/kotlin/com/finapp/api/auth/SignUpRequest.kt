package com.finapp.api.auth

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpRequest(
    @JsonProperty("first_name")
    @NotBlank(message = "first name can not be blanked")
    val firstName: String,
    @JsonProperty("last_name")
    @NotBlank(message = "last name can not be blanked")
    val lastName: String,
    @JsonProperty("email")
    @Email(message = "email must be a valid email")
    val email: String,
    @JsonProperty("credential") val credential: CredentialRequest,
    @JsonProperty("roles") val roles: List<String>
)
