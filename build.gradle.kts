import com.finapp.buildsrc.springBootImplementation
import com.finapp.buildsrc.springBootSwaggerImplementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")

	kotlin("jvm")
	kotlin("plugin.spring")
}

group = "com.finapp"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	springBootImplementation()
	springBootSwaggerImplementation()

	implementation(project(":feature:auth"))
	implementation(project(":feature:users"))
	implementation(project(":feature:products"))
	implementation(project(":feature:roles"))

	implementation(project(":lib:security"))
	implementation(project(":lib:core"))
	implementation(project(":lib:swagger"))
	//implementation(project(":lib:kafka"))
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
