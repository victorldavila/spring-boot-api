package com.finapp.api.security

import com.finapp.api.user_api.data.AuditingUser
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
    fun getCurrentAuditor(): Mono<AuditingUser> {
        return auditorAware.currentAuditor
    }
}