import com.finapp.buildsrc.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
}

group = ApiGroup.Lib.authorize
version = ApiVersion.Lib.authorize

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
    implementation(project(":lib:security"))

    securityLibImplementation()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.test {
    useJUnitPlatform()
}