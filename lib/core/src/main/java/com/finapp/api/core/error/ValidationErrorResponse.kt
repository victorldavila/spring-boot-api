package com.finapp.api.core.error


data class ValidationErrorResponse(
    val violations: MutableList<ViolationResponse> = ArrayList()
)