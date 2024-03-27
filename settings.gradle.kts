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
include("feature:user")

include("lib:security")
include("lib:user-api")
include("lib:authorize")
include("lib:core")
include("feature:products")
findProject(":feature:products")?.name = "products"
include("lib:products-api")
findProject(":lib:products-api")?.name = "products-api"
