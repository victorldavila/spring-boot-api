package com.finapp.api.security.csfle.service

import com.finapp.api.security.csfle.configuration.EncryptedEntity

interface DataEncryptionKeyService {
    fun createOrRetrieveDEK(encryptedEntity: EncryptedEntity?): String?

    open val dataEncryptionKeysB64: Map<String?, String?>?
}