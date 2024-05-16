package com.finapp.api.security

import com.finapp.api.security.token.JwtAuthenticationConverter
import com.finapp.api.security.token.KeyUtils
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import java.security.SecureRandom


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager = true)
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
        //securityContextRepository: SecurityContextRepository,
        jwtAuthenticationConverter: JwtAuthenticationConverter
    ): SecurityWebFilterChain {
        http.cors { it.configurationSource(corsWebFilter()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
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
                it.authenticationEntryPoint(BearerTokenAuthenticationEntryPoint() as? ServerAuthenticationEntryPoint)
                it.accessDeniedHandler(BearerTokenAccessDeniedHandler() as? ServerAccessDeniedHandler)
            }
            //.securityContextRepository(securityContextRepository)

        return http.build()
    }

    @Bean
    @Order(1)
    fun openSecurityWebFilterChain(
        http: ServerHttpSecurity,
        //securityContextRepository: SecurityContextRepository,
        jwtAuthenticationConverter: JwtAuthenticationConverter
    ): SecurityWebFilterChain {
        http
            .cors { it.configurationSource(corsWebFilter()) }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .oauth2ResourceServer {
                it.jwt(Customizer.withDefaults())
            }
            .authorizeExchange{ authorizeSwaggerPaths(it) }
            .authorizeExchange { authorizeApiPaths(it) }
            .authorizeExchange { it.anyExchange().authenticated() }
            .exceptionHandling {
                it.authenticationEntryPoint(BearerTokenAuthenticationEntryPoint() as? ServerAuthenticationEntryPoint)
                it.accessDeniedHandler(BearerTokenAccessDeniedHandler() as? ServerAccessDeniedHandler)
            }
            //.securityContextRepository(securityContextRepository)

        return http.build()
    }

    @Bean
    @Primary
    fun jwtAccessTokenDecoder(keyUtils: KeyUtils): ReactiveJwtDecoder =
        NimbusReactiveJwtDecoder.withPublicKey(keyUtils.accessTokenPublicKey()).build().apply {
            this.setJwtValidator(tokenValidator())
        }

    @Bean
    @Primary
    fun jwtAccessTokenEncoder(keyUtils: KeyUtils): JwtEncoder {
        val jwk = RSAKey
            .Builder(keyUtils.accessTokenPublicKey())
            .privateKey(keyUtils.accessTokenPrivateKey())
            .build()

        return NimbusJwtEncoder(ImmutableJWKSet(JWKSet(jwk)))
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    fun jwtRefreshTokenDecoder(keyUtils: KeyUtils): ReactiveJwtDecoder =
        NimbusReactiveJwtDecoder.withPublicKey(keyUtils.refreshTokenPublicKey()).build()

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    fun jwtRefreshTokenEncoder(keyUtils: KeyUtils): JwtEncoder {
        val jwk = RSAKey
            .Builder(keyUtils.refreshTokenPublicKey())
            .privateKey(keyUtils.refreshTokenPrivateKey())
            .build()

        return NimbusJwtEncoder(ImmutableJWKSet(JWKSet(jwk)))
    }

    private fun tokenValidator(): OAuth2TokenValidator<Jwt> {
        val validators: List<OAuth2TokenValidator<Jwt>> =
            listOf(
                JwtTimestampValidator(),
                JwtIssuerValidator("easyTap"),
            )

        return DelegatingOAuth2TokenValidator(validators)
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