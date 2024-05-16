package com.finapp.api.security.auditor

import org.bson.types.ObjectId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import reactor.core.publisher.Mono

@Configuration
@EnableReactiveMongoAuditing
class MongoConfig(
    private val auditorAware: ApiReactiveAuditorAware
) {
    @Bean
    fun getCurrentAuditor(): Mono<ObjectId?> {
        return auditorAware.currentAuditor
    }
}