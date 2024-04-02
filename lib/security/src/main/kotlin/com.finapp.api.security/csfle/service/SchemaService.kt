package com.finapp.api.security.csfle.service

import com.mongodb.MongoNamespace
import org.bson.BsonDocument
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator

interface SchemaService {
    open var schemasMap: Map<MongoNamespace, BsonDocument>?
    fun generateSchemasMap(schemaCreator: MongoJsonSchemaCreator): Map<MongoNamespace, BsonDocument>
}