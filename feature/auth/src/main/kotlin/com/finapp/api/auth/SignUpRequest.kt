package com.finapp.api.auth

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated

data class SignUpRequest(
    @JsonProperty("first_name")
    @field:NotBlank(message = "first name can not be blanked")
    val firstName: String,
    @JsonProperty("last_name")
    @field:NotBlank(message = "last name can not be blanked")
    val lastName: String,
    @JsonProperty("email")
    @field:Email(message = "email must be a valid email")
    val email: String,
    @JsonProperty("credential")
    @field:Valid
    val credential: CredentialRequest,
    @JsonProperty("roles") val roles: List<String>
)
