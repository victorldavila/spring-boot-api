package com.finapp.api.security

import com.finapp.api.core.model.PermissionType
import com.finapp.api.role.data.RoleItem
import com.finapp.api.users.data.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Component
class TokenGenerator(
    private val accessTokenEncoder: JwtEncoder,
    private val accessTokenDecoder: ReactiveJwtDecoder,
    @Qualifier("jwtRefreshTokenEncoder")
    private val refreshTokenEncoder: JwtEncoder
) {
    fun createAccessToken(user: User, roles: List<RoleItem>): String {

        val now = Instant.now()

        val claimsSet = JwtClaimsSet.builder()
            .issuer("easyTap")
            .issuedAt(now)
            .expiresAt(now.plus(5, ChronoUnit.MINUTES))
            .subject(user.id?.toHexString())
            .claim("scope", roles.map { SimpleGrantedAuthority(it.type).authority }.joinToString(" "))
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

    /**
     * Validate the JWT where it will throw an exception if it isn't valid.
     */
    fun getUserIdByToken(token: String?): String {
        return accessTokenDecoder.decode(token)
            .block()
            .subject
    }

    fun getExpirationDateFromToken(token: String?): Date? {
        return Date.from(
            accessTokenDecoder
                .decode(token)
                .block()
                .expiresAt
        )
    }

    fun validateToken(token: String?): Boolean = try {
        !isTokenExpired(token ?: "")
    } catch (error: JwtException) {
        false
    }

    private fun isTokenExpired(token: String): Boolean =
        getExpirationDateFromToken(token)?.before(Date()) == true

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TokenGenerator::class.java)
    }
}