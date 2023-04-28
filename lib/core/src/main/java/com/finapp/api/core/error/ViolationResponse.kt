package com.finapp.api.core.error

data class ViolationResponse(
    val fieldName: String? = null,
    val message: String? = null
)