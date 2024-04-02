package com.finapp.api.security.csfle.service

import com.mongodb.client.model.Filters.exists
import com.mongodb.client.model.IndexOptions
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.Document
import org.bson.conversions.Bson
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toFlux


/**
 * Initialization of the Key Vault collection and keyAltNames unique index.
 */
@Service
class KeyVaultServiceImpl : KeyVaultService {
    @Value("\${mongodb.key.vault.db}")
    private val keyVaultDB: String? = null

    @Value("\${mongodb.key.vault.coll}")
    private val keyVaultColl: String? = null

    override fun setupKeyVaultCollection(mongoClient: MongoClient) {
        LOGGER.info("=> Setup the key vault collection {}.{}", keyVaultDB, keyVaultColl)

        val db = mongoClient.getDatabase(keyVaultDB ?: "")
        val vault: MongoCollection<Document> = db.getCollection(keyVaultColl ?: "")
        val vaultExists = doesCollectionExist(db, keyVaultColl)

        if (vaultExists) {
            LOGGER.info("=> Vault collection already exists.")

            if (!doesIndexExist(vault)) {
                LOGGER.info("=> Unique index created on the keyAltNames")

                createKeyVaultIndex(vault)
            }
        } else {
            LOGGER.info("=> Creating a new vault collection & index on keyAltNames.")

            createKeyVaultIndex(vault)
        }
    }

    private fun createKeyVaultIndex(vault: MongoCollection<Document>) {
        val keyAltNamesExists: Bson = exists("keyAltNames")

        val indexOpts = IndexOptions()
            .name(INDEX_NAME)
            .partialFilterExpression(keyAltNamesExists)
            .unique(true)

        vault.createIndex(BsonDocument("keyAltNames", BsonInt32(1)), indexOpts)
    }

    private fun doesCollectionExist(db: MongoDatabase, coll: String?): Boolean {
        return db.listCollectionNames()
            .toFlux()
            .filter { it.equals(coll) }
            .collectList()
            .map { it.size > 0 }
            .block() == true
    }

    private fun doesIndexExist(coll: MongoCollection<Document>): Boolean {
        return coll.listIndexes()
            .toFlux()
            .map { it["name"] }
            .filter { it?.equals(INDEX_NAME) == true }
            .collectList()
            .map { it.size > 0 }
            .block() == true
    }

    companion object {
        private const val INDEX_NAME = "uniqueKeyAltNames"

        private val LOGGER = LoggerFactory.getLogger(KeyVaultServiceImpl::class.java)
    }
}