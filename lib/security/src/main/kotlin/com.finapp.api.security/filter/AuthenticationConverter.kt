package com.finapp.api.security.filter

import com.finapp.api.security.model.Security
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationConverter : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> =
        Mono.justOrEmpty(exchange)
            .flatMap { getAuthorizationHeader(it) }
            .filter { isBearerToken(it)}
            .map { bearerToken -> bearerToken?.substring(Security.Header.AUTH_PREFIX.length) }
            .map { token -> UsernamePasswordAuthenticationToken(token, token) }

    private fun getAuthorizationHeader(exchange: ServerWebExchange?) =
        Mono.justOrEmpty(exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION))

    private fun isBearerToken(header: String?) = header?.trim()?.isNotEmpty() == true && header.trim().startsWith(Security.Header.AUTH_PREFIX)
}