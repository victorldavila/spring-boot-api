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
class MongoDBKeyVaultClientConfiguration(private val kmsService: KmsService) {
    @Value("\${spring.data.mongodb.vault.uri}")
    private val CONNECTION_STR: String? = null

    @Value("\${mongodb.key.vault.db}")
    private val KEY_VAULT_DB: String? = null

    @Value("\${mongodb.key.vault.coll}")
    private val KEY_VAULT_COLL: String? = null
    private var KEY_VAULT_NS: MongoNamespace? = null

    @PostConstruct
    fun postConstructor() {
        this.KEY_VAULT_NS = MongoNamespace(KEY_VAULT_DB, KEY_VAULT_COLL)
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
            .applyConnectionString(ConnectionString(CONNECTION_STR))
            .build()

        val ces = ClientEncryptionSettings.builder()
            .keyVaultMongoClientSettings(mcs)
            .keyVaultNamespace(KEY_VAULT_NS!!.fullName)
            .kmsProviders(kmsService.kmsProviders)
            .build()

        return ClientEncryptions.create(ces)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MongoDBKeyVaultClientConfiguration::class.java)
    }
}