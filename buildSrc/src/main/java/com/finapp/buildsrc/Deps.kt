import org.gradle.api.artifacts.dsl.DependencyHandler

object Deps {
    //const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    object SpringBoot {
        val webFlux by lazy { "org.springframework.boot:spring-boot-starter-webflux" }
        val reactiveMongoDb by lazy { "org.springframework.boot:spring-boot-starter-data-mongodb-reactivex" }
        val security by lazy { "org.springframework.boot:spring-boot-starter-security" }
    }

    object Jwt {
        val jwtApi by lazy { "io.jsonwebtoken:jjwt-api:0.11.5" }
        val jwtImpl by lazy { "io.jsonwebtoken:jjwt-impl:0.11.5" }
        val jwtJackson by lazy { "io.jsonwebtoken:jjwt-jackson:0.11.5" }
    }

    object Json {
        val jackson by lazy { "com.fasterxml.jackson.module:jackson-module-kotlin" }
    }

    object Reactor {
        val projectReactor by lazy { "io.projectreactor.kotlin:reactor-kotlin-extensions" }
    }

    object Kotlin {
        val reflect by lazy { "org.jetbrains.kotlin:kotlin-reflect" }
        val stdLib by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8" }
        val coroutinesReactor by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-reactor" }
    }

    private val reactor by lazy {
        arrayListOf<String>().apply {
            add(Reactor.projectReactor)
        }
    }

    private val json by lazy {
        arrayListOf<String>().apply {
            add(Json.jackson)
        }
    }

    private val mongoDb by lazy {
        arrayListOf<String>().apply {
            add(SpringBoot.reactiveMongoDb)
        }
    }

    private val springBoot by lazy {
        arrayListOf<String>().apply {
            add(SpringBoot.webFlux)
            add(SpringBoot.security)
        }
    }

    private val jwt by lazy {
        arrayListOf<String>().apply {
            add(Jwt.jwtApi)
            add(Jwt.jwtImpl)
            add(Jwt.jwtJackson)
        }
    }

    private val kotlin by lazy {
        arrayListOf<String>().apply {
            add(Kotlin.reflect)
        }
    }

    val kotlinRunTime by lazy {
        arrayListOf<String>().apply {
            add(Kotlin.stdLib)
            add(Kotlin.coroutinesReactor)
        }
    }

    val userImplementation by lazy {
        arrayListOf<String>().apply {
            addAll(mongoDb)
            addAll(springBoot)
            addAll(reactor)
            addAll(json)
            addAll(jwt)
            addAll(kotlin)
        }
    }
}