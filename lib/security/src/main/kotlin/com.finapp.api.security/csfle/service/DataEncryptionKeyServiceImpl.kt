package com.finapp.api.security.csfle.service

import com.finapp.api.security.csfle.configuration.EncryptedEntity
import com.mongodb.client.model.vault.DataKeyOptions
import com.mongodb.reactivestreams.client.vault.ClientEncryption
import org.bson.BsonBinary
import org.bson.BsonDocument
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toFlux
import java.util.*


/**
 * Service responsible for creating and remembering the Data Encryption Keys (DEKs).
 * We need to retrieve the DEKs when we evaluate the SpEL expressions in the Entities to create the JSON Schemas.
 */
@Service
class DataEncryptionKeyServiceImpl(
    private val clientEncryption: ClientEncryption
) : DataEncryptionKeyService {
    override val dataEncryptionKeysB64: MutableMap<String?, String?> = mutableMapOf()
        get() {
            LOGGER.info("=> Getting Data Encryption Keys Base64 Map.")
            LOGGER.info("=> Keys in DEK Map: {}", field.entries)

            return field
        }

    @Value("\${mongodb.kms.provider}") private val kmsProvider: String? = null

    override fun createOrRetrieveDEK(encryptedEntity: EncryptedEntity?): String? {
        val b64Encoder: Base64.Encoder = Base64.getEncoder()
        val dekName = encryptedEntity?.dekName
        val dek: BsonDocument? = clientEncryption.getKeyByAltName(dekName ?: "").toFlux().blockFirst()

        val dataKeyId: BsonBinary?

        if (dek == null) {
            LOGGER.info("=> Creating Data Encryption Key: {}", dekName)

            val dko = DataKeyOptions().keyAltNames(listOf(dekName))
            dataKeyId = clientEncryption.createDataKey(kmsProvider ?: "", dko).toFlux().blockFirst()

            LOGGER.debug("=> DEK ID: {}", dataKeyId)
        } else {
            LOGGER.info("=> Existing Data Encryption Key: {}", dekName)

            dataKeyId = dek["_id"]!!.asBinary()

            LOGGER.debug("=> DEK ID: {}", dataKeyId)
        }
        val dek64 = b64Encoder.encodeToString(dataKeyId?.data)

        LOGGER.debug("=> Base64 DEK ID: {}", dek64)
        LOGGER.info(
            "=> Adding Data Encryption Key to the Map with key: {}",
            encryptedEntity?.entityClass?.simpleName
        )

        dataEncryptionKeysB64[encryptedEntity?.entityClass?.simpleName] = dek64

        return dek64
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DataEncryptionKeyServiceImpl::class.java)
    }
}