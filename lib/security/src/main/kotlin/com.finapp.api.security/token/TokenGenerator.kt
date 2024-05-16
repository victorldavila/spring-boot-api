package com.finapp.api.security.token

import com.finapp.api.role.data.RoleItem
import com.finapp.api.users.data.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Component
class TokenGenerator(
    private val accessTokenEncoder: JwtEncoder,
    private val accessTokenDecoder: ReactiveJwtDecoder,
    @Qualifier("jwtRefreshTokenEncoder")
    private val refreshTokenEncoder: JwtEncoder,
    @Qualifier("jwtRefreshTokenDecoder")
    private val refreshTokenDecoder: ReactiveJwtDecoder,
) {
    fun createAccessToken(user: User, roles: List<RoleItem>): String {

        val now = Instant.now()

        val claimsSet = JwtClaimsSet.builder()
            .issuer("easyTap")
            .issuedAt(now)
            .expiresAt(now.plus(5, ChronoUnit.MINUTES))
            .subject(user.id?.toHexString())
            .claim("scope", roles.joinToString(" ") { SimpleGrantedAuthority(it.type).authority })
            .build()

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    fun createRefreshToken(user: User): String {
        val now = Instant.now()

        val claimsSet = JwtClaimsSet.builder()
            .issuer("easyTap")
            .issuedAt(now)
            .expiresAt(now.plus(30, ChronoUnit.DAYS))
            .subject(user.id?.toHexString())
            .build()

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).tokenValue
    }

    fun getClaimRole(token: String?): Mono<List<GrantedAuthority>> =
        accessTokenDecoder.decode(token)
            .map { it.getClaim("scope") as String }
            .map { it.split(" ").map { role -> SimpleGrantedAuthority(role) } }

    fun getUserIdByAccessToken(token: String?): Mono<String> {
        return accessTokenDecoder.decode(token)
            .map { it.subject }
    }

    fun getUserIdByRefreshToken(token: String?): Mono<String> {
        return refreshTokenDecoder.decode(token)
            .map { it.subject }
    }

    fun getExpirationDateFromAccessToken(token: String?): Mono<Date> {
        return accessTokenDecoder
                .decode(token)
                .map { Date.from(it.expiresAt) }
    }

    fun getExpirationDateFromRefreshToken(token: String?): Mono<Date> {
        return refreshTokenDecoder
            .decode(token)
            .map { Date.from(it.expiresAt) }
    }

    fun validateAccessToken(token: String?): Mono<Boolean> =
        isAccessTokenExpired(token ?: "")
            .onErrorResume { Mono.just(false) }

    fun validateRefreshToken(token: String?): Mono<Boolean> =
        isRefreshTokenExpired(token ?: "")
            .onErrorResume { Mono.just(false) }

    private fun isAccessTokenExpired(token: String): Mono<Boolean> =
        getExpirationDateFromAccessToken(token)
            .map { it.before(Date()) }

    private fun isRefreshTokenExpired(token: String): Mono<Boolean> =
        getExpirationDateFromRefreshToken(token)
            .map { it.before(Date()) }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TokenGenerator::class.java)
    }
}