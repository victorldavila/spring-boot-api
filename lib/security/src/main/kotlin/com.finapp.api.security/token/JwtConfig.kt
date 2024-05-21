package com.finapp.api.security.token

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*

@Configuration
class JwtConfig {
    @Autowired
    lateinit var tokenBlackListValidator: TokenBlackListValidator

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
                tokenBlackListValidator
            )

        return DelegatingOAuth2TokenValidator(validators)
    }
}