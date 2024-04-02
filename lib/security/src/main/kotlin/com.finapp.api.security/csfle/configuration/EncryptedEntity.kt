package com.finapp.api.security.csfle.configuration

import com.mongodb.MongoNamespace


/**
 * This class contains the metadata about the encrypted collections.
 */
data class EncryptedEntity(
    private val database: String,
    private val collection: String,
    var entityClass: Class<*>,
    var dekName: String
) {
    var namespace: MongoNamespace = MongoNamespace(database, collection)
}