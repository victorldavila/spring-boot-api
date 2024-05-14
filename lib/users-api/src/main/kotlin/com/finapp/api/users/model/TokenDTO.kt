package com.finapp.api.users.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenDTO(
    @JsonProperty("tyype") val type: String,
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("refresh_token") val refreshToken: String
)
