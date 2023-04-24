package com.finapp.buildsrc

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.runtimeOnly(list: List<String>) {
    list.forEach { dependency ->
        add("runtimeOnly", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}