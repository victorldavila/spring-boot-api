package com.finapp.api.security.csfle.service

import com.finapp.api.security.csfle.configuration.EncryptedCollectionsConfiguration
import com.finapp.api.security.csfle.configuration.EncryptedEntity
import com.mongodb.MongoNamespace
import org.bson.BsonDocument
import org.bson.json.JsonWriterSettings
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator
import org.springframework.stereotype.Service


@Service
class SchemaServiceImpl : SchemaService {
    override var schemasMap: Map<MongoNamespace, BsonDocument>? = null

    override fun generateSchemasMap(schemaCreator: MongoJsonSchemaCreator): Map<MongoNamespace, BsonDocument> {
        LOGGER.info("=> Generating schema map.")

        val encryptedEntities: List<EncryptedEntity> = EncryptedCollectionsConfiguration.encryptedEntities

        return encryptedEntities
            .associate { it.namespace to generateSchema(schemaCreator, it.entityClass) }
            .also { schemasMap = it }
    }

    private fun generateSchema(schemaCreator: MongoJsonSchemaCreator, entityClass: Class<*>): BsonDocument {
        val schema = schemaCreator.filter(MongoJsonSchemaCreator.encryptedOnly())
            .createSchemaFor(entityClass)
            .schemaDocument()
            .toBsonDocument()

        LOGGER.info(
            "=> JSON Schema for {}:\n{}", entityClass.simpleName,
            schema.toJson(JsonWriterSettings.builder().indent(true).build())
        )

        return schema
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SchemaServiceImpl::class.java)
    }
}