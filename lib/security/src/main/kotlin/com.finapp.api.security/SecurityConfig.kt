package com.finapp.api.security

import com.finapp.api.security.repository.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.core.publisher.Mono
import java.security.SecureRandom


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        val strength = 10
        return BCryptPasswordEncoder(strength, SecureRandom())
    }

    @Bean
    @Order(2)
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        reactiveAuthenticationManager: ReactiveAuthenticationManager,
        jwtAuthenticationConverter: ServerAuthenticationConverter,
        securityContextRepository: SecurityContextRepository
    ): SecurityWebFilterChain {

        val authenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager).apply {
            setServerAuthenticationConverter(jwtAuthenticationConverter)
        }

        http.cors { it.configurationSource(corsWebFilter()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .securityMatcher {
                ServerWebExchangeMatchers.matchers(
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/**"),
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.PUT, "/**"),
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE, "/**"),
                    ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/**")
                ).matches(it)
            }
            .authorizeExchange { it.anyExchange().authenticated() }
            .exceptionHandling {
                it.authenticationEntryPoint { swe, _ -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED } }
                it.accessDeniedHandler { swe, _ -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN } }
            }
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(securityContextRepository)

        return http.build()
    }

    @Bean
    @Order(1)
    fun openSecurityWebFilterChain(
        http: ServerHttpSecurity,
        authManager: ReactiveAuthenticationManager,
        authConverter: ServerAuthenticationConverter,
        securityContextRepository: SecurityContextRepository
    ): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(authManager).apply {
            setServerAuthenticationConverter(authConverter)
        }

        http
            .cors { it.configurationSource(corsWebFilter()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .authorizeExchange{ authorizeSwaggerPaths(it) }
            .authorizeExchange { authorizeApiPaths(it) }
            .authorizeExchange { it.anyExchange().authenticated() }
            .exceptionHandling {
                it.authenticationEntryPoint { swe, _ -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED } }
                it.accessDeniedHandler { swe, _ -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN } }
            }
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(securityContextRepository)

        return http.build()
    }

    private fun authorizeApiPaths(auth: AuthorizeExchangeSpec) {
        auth.pathMatchers(HttpMethod.GET,"/health").permitAll()
        auth.pathMatchers(HttpMethod.POST,"/v1/auth/signin").permitAll()
        auth.pathMatchers(HttpMethod.POST,"/v1/auth/signup").permitAll()
        auth.pathMatchers(HttpMethod.POST,"/v1/auth/refresh").permitAll()
        auth.pathMatchers(HttpMethod.DELETE,"/v1/auth/signout").permitAll()
    }

    private fun corsWebFilter(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("*")
        corsConfig.maxAge = 8000L
        corsConfig.addAllowedMethod("GET")
        corsConfig.addAllowedMethod("POST")
        corsConfig.addAllowedMethod("PUT")
        corsConfig.addAllowedMethod("DELETE")
        corsConfig.addAllowedMethod("OPTIONS")

        corsConfig.addAllowedHeader("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }

    private fun authorizeSwaggerPaths(auth: AuthorizeExchangeSpec) {
        auth.pathMatchers(
            "/actuator",
            "/actuator/**",
            "/v1/finapp-docs",
            "/v1/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v1/webjars/**",
            "/swagger-resources/**",
            "/*/swagger-resources/**",
            "/*/v3/api-docs"
        ).permitAll()
    }
}