package com.finapp.api.users.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.finapp.api.core.validation.OnCreate
import com.finapp.api.core.validation.OnUpdate
import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @JsonProperty("first_name")
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "first name can not be blanked")
    val firstName: String?,
    @JsonProperty("last_name")
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "last name can not be blanked")
    val lastName: String?,
    @JsonProperty("email")
    @field:NotBlank(groups = [ OnCreate::class, OnUpdate::class ], message = "email can not be blanked")
    val email: String?,
    @JsonProperty("username")
    val username: String?
)
