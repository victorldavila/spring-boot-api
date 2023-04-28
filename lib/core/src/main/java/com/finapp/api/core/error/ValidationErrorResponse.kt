package com.finapp.api.core.error


data class ValidationErrorResponse(
    val error: MutableList<ViolationResponse> = ArrayList()
)