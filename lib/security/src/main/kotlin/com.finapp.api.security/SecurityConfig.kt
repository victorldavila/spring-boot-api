package com.finapp.api.security

import com.finapp.api.security.repository.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
class ApplicationSecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        authManager: ReactiveAuthenticationManager,
        authConverter: ServerAuthenticationConverter,
        securityContextRepository: SecurityContextRepository
    ): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(authManager).apply {
            setServerAuthenticationConverter(authConverter)
        }

        http.cors().and().csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .authorizeExchange{ authorizeSwaggerPaths(it) }
            .authorizeExchange { authorizeApiPaths(it) }
            .authorizeExchange { it.anyExchange().authenticated() }
            .exceptionHandling()
            .authenticationEntryPoint{ swe, _ -> Mono.fromRunnable{ swe.response.statusCode = HttpStatus.UNAUTHORIZED } }
            .accessDeniedHandler { swe, _ -> Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN } }
            .and()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .securityContextRepository(securityContextRepository)

        return http.build()
    }

    private fun authorizeApiPaths(auth: AuthorizeExchangeSpec) {
        auth.pathMatchers(HttpMethod.GET,"/health").permitAll()
        auth.pathMatchers(HttpMethod.POST,"/v1/auth/login").permitAll()
        auth.pathMatchers(HttpMethod.POST,"/v1/auth/signup").permitAll()
        auth.pathMatchers(HttpMethod.GET,"/v1/auth/refresh").permitAll()
        auth.pathMatchers(HttpMethod.DELETE,"/v1/auth/logout").permitAll()
    }

    private fun authorizeSwaggerPaths(auth: AuthorizeExchangeSpec) {
        auth.pathMatchers(
            "/actuator",
            "/actuator/**",
            "/v1/dexpay-docs",
            "/v1/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/*/swagger-resources/**",
            "/*/v3/api-docs"
        ).permitAll()
    }
}