import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.finapp.buildsrc.ApiGroup
import com.finapp.buildsrc.ApiVersion
import com.finapp.buildsrc.userLibImplementation

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

group = ApiGroup.Feature.USERS
version = ApiVersion.Feature.USERS


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
    implementation(project(":lib:users-api"))
    implementation(project(":lib:roles-api"))

    implementation(project(":lib:security"))
    implementation(project(":lib:core"))

    userLibImplementation()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
}