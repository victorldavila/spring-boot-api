package com.finapp.api.security

import com.mongodb.*
import com.mongodb.client.model.Filters
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.vault.DataKeyOptions
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.vault.ClientEncryption
import com.mongodb.reactivestreams.client.vault.ClientEncryptions
import kotlinx.coroutines.reactive.awaitLast
import org.bson.BsonBinary
import org.bson.BsonDocument
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import reactor.core.publisher.Mono
import java.io.File




@Configuration
@EnableReactiveMongoAuditing
class MongoConfig(
    private val auditorAware: ApiReactiveAuditorAware
) {

    @Bean
    fun getCurrentAuditor(): Mono<ObjectId> {
        return auditorAware.currentAuditor
    }
}