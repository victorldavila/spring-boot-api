import com.finapp.buildsrc.ApiGroup
import com.finapp.buildsrc.ApiVersion
import com.finapp.buildsrc.baseLibsImplementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

group = ApiGroup.Feature.PRODUCTS_COMBINATION
version = ApiVersion.Feature.PRODUCTS_COMBINATION


java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}


dependencies {
    implementation(project(":lib:products-api"))
    implementation(project(":lib:products-combination-api"))

    implementation(project(":lib:security"))
    implementation(project(":lib:core"))

    baseLibsImplementation()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
}