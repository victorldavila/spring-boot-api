package com.finapp.api.security.filter

import com.finapp.api.security.jwt.JwtHelper
import com.finapp.api.user_api.token.repository.TokenRepository
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(
    private val jwtHelper: JwtHelper,
    private val tokenRepository: TokenRepository
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
            .map { it.credentials.toString() }
            .filter { jwtHelper.validateJwtToken(it) }
            .flatMap { getUserWithValidAccessToken(it) }
            .onErrorResume { Mono.empty() }
            .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Credentials")))
            .map { token ->
                UsernamePasswordAuthenticationToken(
                    token.access,
                    token.access,
                    getRoles(jwtHelper.getAllClaimsFromToken(token.access))
                )
            }
    }

    private fun getRoles(claims: Claims?): MutableList<GrantedAuthority>? {
        val roles = (claims?.get("role") as? List<*>)

        return roles
            //?.map { it as HashMap<*, *> }
            ?.map { SimpleGrantedAuthority(it.toString()) }
            ?.toMutableList()
    }

    private fun getUserWithValidAccessToken(accessToken: String) =
        tokenRepository.findTokenByUsername(jwtHelper.getUserNameFromJwtToken(accessToken))
            .filter { it.access == accessToken }
            .collectList()
            .map { it.first() }
}