package com.finapp.buildsrc

object ApiDeps {
    object SpringBoot {
        val validation by lazy { "org.springframework.boot:spring-boot-starter-validation" }
        val webFlux by lazy { "org.springframework.boot:spring-boot-starter-webflux" }
        val reactiveMongoDb by lazy { "org.springframework.boot:spring-boot-starter-data-mongodb-reactive" }
        val security by lazy { "org.springframework.boot:spring-boot-starter-security" }
        val rsocket by lazy { "org.springframework.boot:spring-boot-starter-rsocket" }
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
    }

    object Kotlin {
        val reflect by lazy { "org.jetbrains.kotlin:kotlin-reflect" }
        val stdLib by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8" }
        val coroutinesReactor by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-reactor" }
    }
}