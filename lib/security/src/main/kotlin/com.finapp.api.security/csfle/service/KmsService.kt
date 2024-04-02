package com.finapp.api.security.csfle.service

interface KmsService {
    open val kmsProviders: Map<String?, Map<String, Any>>
}