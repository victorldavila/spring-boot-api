import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.finapp.buildsrc.ApiGroup
import com.finapp.buildsrc.ApiVersion
import com.finapp.buildsrc.kafkaLibImplementation

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

group = ApiGroup.Lib.KAFKA
version = ApiVersion.Lib.KAFKA


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
    implementation(project(":lib:core"))
    implementation(project(":lib:users-api"))

    kafkaLibImplementation()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
}