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
}

fun DependencyHandler.springBootSecurityImplementation() {
    this.add("implementation", ApiDeps.SpringBoot.security)
}

fun DependencyHandler.projectReactorImplementation() {
    this.add("implementation", ApiDeps.Reactor.projectReactor)
}

fun DependencyHandler.jacksonImplementation() {
    this.add("implementation", ApiDeps.Json.jackson)
}

fun DependencyHandler.securityLibImplementation() {
    springBootImplementation()
    springBootSecurityImplementation()
    jwtImplementation()
    jacksonImplementation()
    projectReactorImplementation()
    kotlinImplementation()
}

fun DependencyHandler.userLibImplementation() {
    springBootImplementation()
    jacksonImplementation()
    projectReactorImplementation()
    kotlinImplementation()
}