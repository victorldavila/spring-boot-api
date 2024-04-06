package com.finapp.api.security.csfle.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


/**
 * Key Management System Service.
 * Avoid using a local file to store your Master Key in production.
 * Use a proper KMS provider instead.
 * [MongoDB KMS provider documentation](https://www.mongodb.com/docs/manual/core/csfle/reference/kms-providers/#supported-key-management-services)
 */
@Service
class KmsServiceImpl(
    private val masterKeyService: MasterKeyService
) : KmsService {

    @Value("\${mongodb.kms.provider}") private val local: String? = null

    override val kmsProviders: Map<String?, Map<String, Any>>
        get() {
            LOGGER.info("=> Creating local Key Management System using the master key.")
            return hashMapOf(
                local to hashMapOf(
                    "key" to masterKeyService.generateNewOrRetrieveMasterKeyFromFile()
                )
            )
        }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KmsServiceImpl::class.java)
    }
}