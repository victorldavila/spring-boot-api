pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.2.5"
        id("io.spring.dependency-management") version "1.1.4"
        kotlin("jvm") version "1.9.23"
        kotlin("plugin.spring") version "1.9.23"
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
include("lib:kafka")

include("lib:users-api")
include("lib:products-api")
include("lib:roles-api")