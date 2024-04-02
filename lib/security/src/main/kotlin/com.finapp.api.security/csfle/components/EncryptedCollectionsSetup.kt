package com.finapp.api.security.csfle.components

import com.finapp.api.security.csfle.service.SchemaService
import com.mongodb.MongoNamespace
import com.mongodb.client.model.CreateCollectionOptions
import com.mongodb.client.model.ValidationOptions
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase
import jakarta.annotation.PostConstruct
import org.bson.BsonDocument
import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toFlux


/**
 * Create or update the encrypted collections with a server side JSON Schema to secure the encrypted field in the MongoDB database.
 * This prevents any other client from inserting or editing the fields without encrypting the fields correctly.
 */
@Component
class EncryptedCollectionsSetup(
    private val mongoClient: MongoClient,
    private val schemaService: SchemaService
) {
    @PostConstruct
    fun postConstruct() {
        LOGGER.info("=> Setup the encrypted collections.")

        schemaService.schemasMap?.forEach { (namespace, schema) ->
            createOrUpdateCollection(mongoClient, namespace, schema)
        }
    }

    private fun createOrUpdateCollection(mongoClient: MongoClient, ns: MongoNamespace, schema: BsonDocument) {
        val db = mongoClient.getDatabase(ns.databaseName)
        val collStr = ns.collectionName

        if (doesCollectionExist(db, ns)) {
            LOGGER.info("=> Updating {} collection's server side JSON Schema.", ns.fullName)

            db.runCommand(Document("collMod", collStr).append("validator", jsonSchemaWrapper(schema)))
        } else {
            LOGGER.info("=> Creating encrypted collection {} with server side JSON Schema.", ns.fullName)

            db.createCollection(
                collStr, CreateCollectionOptions().validationOptions(
                    ValidationOptions().validator(jsonSchemaWrapper(schema))
                )
            )
        }
    }

    fun jsonSchemaWrapper(schema: BsonDocument?): BsonDocument {
        return BsonDocument("\$jsonSchema", schema)
    }

    private fun doesCollectionExist(db: MongoDatabase, ns: MongoNamespace): Boolean {
        return db.listCollectionNames()
            .toFlux()
            .filter { it.equals(ns.collectionName) }
            .collectList()
            .map { it.size > 0 }
            .block() == true
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(EncryptedCollectionsSetup::class.java)
    }
}