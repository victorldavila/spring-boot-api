package com.finapp.api.security.csfle.configuration

import com.finapp.api.security.csfle.service.KmsService
import com.finapp.api.security.csfle.service.SchemaService
import com.mongodb.AutoEncryptionSettings
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoNamespace
import jakarta.annotation.PostConstruct
import org.bson.BsonDocument
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.mapping.context.MappingContext
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator


@Configuration
@DependsOn("keyVaultAndDekSetup")
class MongoDBSecureClientConfiguration(
    private val kmsService: KmsService,
    private val schemaService: SchemaService
) {
    @Value("\${crypt.shared.lib.path}") private val cryptShareLibPath: String? = null
    @Value("\${spring.data.mongodb.storage.uri}") private val connectionStrData: String? = null
    @Value("\${spring.data.mongodb.vault.uri}") private val connectionStrVault: String? = null
    @Value("\${mongodb.key.vault.db}") private val keyVaultDB: String? = null
    @Value("\${mongodb.key.vault.coll}") private val keyVaultColl: String? = null

    private var keyVaultNS: MongoNamespace? = null

    @PostConstruct
    fun postConstruct() {
        this.keyVaultNS = MongoNamespace(keyVaultDB ?: "", keyVaultColl ?: "")
    }

    @Bean
    fun mongoClientSettings(): MongoClientSettings {
        LOGGER.info("=> Creating the MongoClientSettings for the encrypted collections.")

        return MongoClientSettings
            .builder()
            .applyConnectionString(ConnectionString(connectionStrData ?: ""))
            .build()
    }

    @Bean
    fun customizer(mappingContext: MappingContext<*, *>): MongoClientSettingsBuilderCustomizer {
        LOGGER.info("=> Creating the MongoClientSettingsBuilderCustomizer.")
        return MongoClientSettingsBuilderCustomizer { builder: MongoClientSettings.Builder ->
            val schemaCreator = MongoJsonSchemaCreator.create(mappingContext)
            val schemaMap: Map<String, BsonDocument> = schemaService.generateSchemasMap(schemaCreator)
                .entries
                .associate { it.key.fullName to it.value }

            val extraOptions = mapOf<String, Any?>(
                    "cryptSharedLibPath" to cryptShareLibPath,
                    "cryptSharedLibRequired" to true
                )

            val mcs = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(connectionStrVault ?: ""))
                .build()

            val oes = AutoEncryptionSettings.builder()
                .keyVaultMongoClientSettings(mcs)
                .keyVaultNamespace(keyVaultNS!!.fullName)
                .kmsProviders(kmsService.kmsProviders)
                .schemaMap(schemaMap)
                .extraOptions(extraOptions)
                .build()

            builder.autoEncryptionSettings(oes)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MongoDBSecureClientConfiguration::class.java)
    }
}