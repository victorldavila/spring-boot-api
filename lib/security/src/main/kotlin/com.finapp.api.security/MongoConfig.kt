package com.finapp.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing


@Configuration
@EnableReactiveMongoAuditing
class MongoConfig {
//    @Bean
//    fun myAuditorProvider(): AuditorAware<AuditableUser> {
//        return AuditorAwareImpl()
//    }
}