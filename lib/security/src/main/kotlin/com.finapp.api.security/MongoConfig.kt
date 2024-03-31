package com.finapp.api.security

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import reactor.core.publisher.Mono


@Configuration
@EnableReactiveMongoAuditing
class MongoConfig {
    @Autowired
    private lateinit var auditorAware: ApiReactiveAuditorAware

    @Bean
    fun getCurrentAuditor(): Mono<ObjectId> {
        return auditorAware.currentAuditor
    }
}