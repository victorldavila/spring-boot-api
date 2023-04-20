pluginManagement {
    plugins {
        id("org.springframework.boot") version "2.7.10"
        id("io.spring.dependency-management") version "1.1.0"
        kotlin("jvm") version "1.7.22"
        kotlin("plugin.spring") version "1.7.22"
    }
}

rootProject.name = "finapp"
include("lib:security")
findProject(":lib:security")?.name = "security"
