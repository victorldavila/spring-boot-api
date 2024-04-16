package com.finapp.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.jwtImplementation() {
    this.add("implementation", ApiDeps.Jwt.jwtApi)
    this.add("runtimeOnly", ApiDeps.Jwt.jwtImpl)
    this.add("runtimeOnly", ApiDeps.Jwt.jwtJackson)
}

fun DependencyHandler.kotlinImplementation() {
    this.add("implementation", ApiDeps.Kotlin.reflect)
    this.add("implementation", ApiDeps.Kotlin.stdLib)
    this.add("implementation", ApiDeps.Kotlin.coroutinesReactor)
}

fun DependencyHandler.springBootImplementation() {
    this.add("implementation", ApiDeps.SpringBoot.validation)
    this.add("implementation", ApiDeps.SpringBoot.webFlux)
    this.add("implementation", ApiDeps.SpringBoot.reactiveMongoDb)
    this.add("implementation", ApiDeps.SpringBoot.actuator)
}

fun DependencyHandler.springBootSwaggerImplementation() {
    this.add("implementation", ApiDeps.OpenApi.ui)
}

fun DependencyHandler.springBootReactiveSocketImplementation() {
    this.add("implementation", ApiDeps.SpringBoot.rsocket)
}

fun DependencyHandler.springBootKafkaImplementation() {
    this.add("implementation", ApiDeps.SpringBoot.cloudStreamKafka)
    this.add("implementation", ApiDeps.SpringBoot.cloudStream)
    this.add("implementation", ApiDeps.SpringBoot.cloudFunctionKotlin)
    //this.add("implementation", ApiDeps.Kafka.streams)
}

fun DependencyHandler.springBootSecurityImplementation() {
    this.add("implementation", ApiDeps.SpringBoot.security)
}

fun DependencyHandler.projectReactorImplementation() {
    this.add("implementation", ApiDeps.Reactor.projectReactor)
    this.add("implementation", ApiDeps.Reactor.reactorExtra)
}

fun DependencyHandler.jacksonImplementation() {
    this.add("implementation", ApiDeps.Json.jackson)
}

fun DependencyHandler.mongoDbImplementation() {
    this.add("implementation", ApiDeps.MongoDb.crypt)
}

fun DependencyHandler.baseLibsImplementation() {
    springBootImplementation()
    jacksonImplementation()
    projectReactorImplementation()
    kotlinImplementation()
}

fun DependencyHandler.securityLibImplementation() {
    springBootImplementation()
    springBootSecurityImplementation()
    jwtImplementation()
    jacksonImplementation()
    projectReactorImplementation()
    kotlinImplementation()
    springBootReactiveSocketImplementation()
    mongoDbImplementation()
}

fun DependencyHandler.kafkaLibImplementation() {
    baseLibsImplementation()
    springBootKafkaImplementation()
}

fun DependencyHandler.userLibImplementation() {
    baseLibsImplementation()
    springBootReactiveSocketImplementation()
}