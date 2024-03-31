pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.0.6"
        id("io.spring.dependency-management") version "1.1.0"
        kotlin("jvm") version "1.7.22"
        kotlin("plugin.spring") version "1.7.22"
    }
}

rootProject.name = "finapp"

include("feature:auth")
include("feature:users")
include("feature:products")
include("feature:roles")

include("lib:security")
include("lib:authorize")
include("lib:core")
include("lib:swagger")

include("lib:users-api")
include("lib:products-api")
include("lib:roles-api")
