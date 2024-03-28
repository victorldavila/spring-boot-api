package com.finapp.api.security

import com.finapp.api.user_api.data.User
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import reactor.core.publisher.Mono


@Configuration
@EnableReactiveMongoAuditing
class MongoConfig {
    @Autowired
    private lateinit var auditorAware: ReactiveAuditorAware
    fun getCurrentAuditor(): Mono<ObjectId> {
        return auditorAware.currentAuditor
    }
}