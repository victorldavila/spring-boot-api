package com.finapp.api.users.model

import com.finapp.api.core.validation.OnDelete
import com.finapp.api.core.validation.OnRead
import com.finapp.api.core.validation.OnUpdate
import com.finapp.api.core.validation.ObjectId
import jakarta.validation.Valid

data class UserArg(
    @field:Valid
    val userParam: UserParam? = null,
    @field:Valid
    val request: UserRequest? = null
)

data class UserParam(
    @field:ObjectId(groups = [ OnUpdate::class, OnDelete::class, OnRead::class ], message = "user id must be valid")
    val userId: String?
)
