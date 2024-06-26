package com.finapp.api.security.csfle.configuration

import com.finapp.api.security.csfle.service.KmsService
import com.mongodb.ClientEncryptionSettings
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoNamespace
import com.mongodb.reactivestreams.client.vault.ClientEncryption
import com.mongodb.reactivestreams.client.vault.ClientEncryptions
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * ClientEncryption used by the DataEncryptionKeyService to create the DEKs.
 */
@Configuration
class MongoDBKeyVaultClientConfiguration(
    private val kmsService: KmsService
) {
    @Value("\${spring.data.mongodb.vault.uri}") private val connectionStr: String? = null
    @Value("\${mongodb.key.vault.db}") private val keyVaultDB: String? = null
    @Value("\${mongodb.key.vault.coll}") private val keyVaultColl: String? = null

    private var keyVaultNS: MongoNamespace? = null

    @PostConstruct
    fun postConstructor() {
        this.keyVaultNS = MongoNamespace(keyVaultDB ?: "", keyVaultColl ?: "")
    }

    /**
     * MongoDB Encryption Client that can manage Data Encryption Keys (DEKs).
     *
     * @return ClientEncryption MongoDB connection that can create or delete DEKs.
     */
    @Bean
    fun clientEncryption(): ClientEncryption {
        LOGGER.info("=> Creating the MongoDB Key Vault Client.")

        val mcs = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionStr ?: ""))
            .build()

        val ces = ClientEncryptionSettings.builder()
            .keyVaultMongoClientSettings(mcs)
            .keyVaultNamespace(keyVaultNS!!.fullName)
            .kmsProviders(kmsService.kmsProviders)
            .build()

        return ClientEncryptions.create(ces)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MongoDBKeyVaultClientConfiguration::class.java)
    }
}