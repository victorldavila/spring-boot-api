package com.finapp.api.security.jwt

import com.finapp.api.core.model.ProfilePermissionType
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtHelper {
    @Value("\${finapp.jwt.secret}")
    private val jwtSecret: String? = null

    @Value("\${finapp.jwt.expirationMs}")
    private val jwtExpirationMs = 0

    private val key: SecretKey? by lazy { Keys.hmacShaKeyFor(jwtSecret?.toByteArray()) }

    fun generateToken(roles: List<String>, username: String, isRefreshToken: Boolean = false): String = generateJwtToken(username, hashMapOf("roles" to roles), isRefreshToken)

    private fun generateJwtToken(username: String, claims: Map<String, Any>, isRefreshToken: Boolean): String {
        val jwt = Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date())
            .signWith(key)

        return if (!isRefreshToken) {
            jwt.setExpiration(Date(Date().time + jwtExpirationMs))
        } else {
            jwt
        }.compact()
    }

    fun getUserNameFromJwtToken(token: String?): String {
        return getAllClaimsFromToken(token)?.subject ?: ""
    }

    /**
     * Validate the JWT where it will throw an exception if it isn't valid.
     */
    fun getAllClaimsFromToken(token: String?): Claims? {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getExpirationDateFromToken(token: String?): Date? {
        return getAllClaimsFromToken(token)?.expiration
    }

    fun validateToken(token: String?): Boolean = try {
        !isTokenExpired(token ?: "")
    } catch (error: SignatureException) {
        false
    }

    private fun isTokenExpired(token: String): Boolean = getExpirationDateFromToken(token)?.before(Date()) == true

    private fun getPermissionName(permissionType: String) = ProfilePermissionType.getPermissionByType(permissionType).permissionName

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            return validateToken(authToken)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    fun getRoles(token: String?): MutableList<GrantedAuthority>? {
        val claims = getAllClaimsFromToken(token)
        val roles = (claims?.get("roles") as? List<*>)

        return roles
            ?.asSequence()
            ?.map { it.toString() }
            ?.map { getPermissionName(it) }
            ?.map { SimpleGrantedAuthority(it) }
            ?.toMutableList()
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtHelper::class.java)
    }
}