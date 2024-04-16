package com.finapp.buildsrc

object ApiDeps {
    object SpringBoot {
        val validation by lazy { "org.springframework.boot:spring-boot-starter-validation" }
        val webFlux by lazy { "org.springframework.boot:spring-boot-starter-webflux" }
        val reactiveMongoDb by lazy { "org.springframework.boot:spring-boot-starter-data-mongodb-reactive" }
        val security by lazy { "org.springframework.boot:spring-boot-starter-security" }
        val actuator by lazy { "org.springframework.boot:spring-boot-starter-actuator" }
        val rsocket by lazy { "org.springframework.boot:spring-boot-starter-rsocket" }
        val cloudStream by lazy { "org.springframework.cloud:spring-cloud-stream:4.1.1" }
        val cloudStreamKafka by lazy { "org.springframework.cloud:spring-cloud-stream-binder-kafka:4.1.1" }
        val cloudFunctionKotlin by lazy { "org.springframework.cloud:spring-cloud-function-kotlin:4.1.1" }
        //val kafkaStream by lazy { "org.springframework.cloud:spring-cloud-stream-binder-kafka-streams:4.1.1" }
    }

    object Kafka {
        val streams by lazy { "org.apache.kafka:kafka-streams" }
    }

    object OpenApi {
        //val core by lazy { "org.springdoc:springdoc-openapi-webflux-core:1.4.3" }
        val ui by lazy { "org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.4" }
    }

    object MongoDb {
        //val core by lazy { "org.springdoc:springdoc-openapi-webflux-core:1.4.3" }
        val crypt by lazy { "org.mongodb:mongodb-crypt:1.8.0" }
    }

    object Jwt {
        val jwtApi by lazy { "io.jsonwebtoken:jjwt-api:0.11.5" }
        val jwtImpl by lazy { "io.jsonwebtoken:jjwt-impl:0.11.5" }
        val jwtJackson by lazy { "io.jsonwebtoken:jjwt-jackson:0.11.5" }
    }

    object Json {
        val jackson by lazy { "com.fasterxml.jackson.module:jackson-module-kotlin" }
    }

    object Reactor {
        val projectReactor by lazy { "io.projectreactor.kotlin:reactor-kotlin-extensions" }
        val reactorExtra by lazy { "io.projectreactor.addons:reactor-extra" }
    }

    object Kotlin {
        val reflect by lazy { "org.jetbrains.kotlin:kotlin-reflect" }
        val stdLib by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8" }
        val coroutinesReactor by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-reactor" }
    }
}