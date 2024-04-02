package com.finapp.api.security.csfle.service

interface MasterKeyService {
    fun generateNewOrRetrieveMasterKeyFromFile(): ByteArray
}