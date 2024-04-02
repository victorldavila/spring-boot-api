package com.finapp.api.security.csfle.components

import com.finapp.api.security.csfle.configuration.EncryptedCollectionsConfiguration
import com.finapp.api.security.csfle.configuration.EncryptedEntity
import com.finapp.api.security.csfle.service.DataEncryptionKeyService
import com.finapp.api.security.csfle.service.KeyVaultService
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.function.Consumer


/**
 * This class initialize the Key Vault (collection + keyAltNames unique index) using a dedicated standard connection
 * to MongoDB.
 * Then it creates the Data Encryption Keys (DEKs) required to encrypt the documents in each of the
 * encrypted collections.
 */
@Component
class KeyVaultAndDekSetup(
    private val keyVaultService: KeyVaultService,
    private val dataEncryptionKeyService: DataEncryptionKeyService
) {
    @Value("\${spring.data.mongodb.vault.uri}")
    private val connectionStr: String? = null

    @PostConstruct
    fun postConstruct() {
        LOGGER.info("=> Start Encryption Setup.")
        LOGGER.debug("=> MongoDB Connection String: {}", connectionStr)

        val mcs = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionStr ?: ""))
            .build()

        try {
            MongoClients.create(mcs).use { client ->
                LOGGER.info("=> Created the MongoClient instance for the encryption setup.")
                LOGGER.info("=> Creating the encryption key vault collection.")

                keyVaultService.setupKeyVaultCollection(client)

                LOGGER.info("=> Creating the Data Encryption Keys.")

                EncryptedCollectionsConfiguration.encryptedEntities.forEach(
                    Consumer { encryptedEntity: EncryptedEntity? ->
                        dataEncryptionKeyService.createOrRetrieveDEK(encryptedEntity)
                    }
                )

                LOGGER.info("=> Encryption Setup completed.")
            }
        } catch (e: Exception) {
            LOGGER.error("=> Encryption Setup failed: {}", e.message, e)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KeyVaultAndDekSetup::class.java)
    }
}