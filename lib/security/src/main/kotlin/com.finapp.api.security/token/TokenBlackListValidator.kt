package com.finapp.api.security.token

import com.finapp.api.users.repository.TokenRepository
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class TokenBlackListValidator(
    private val tokenRepository: TokenRepository
): OAuth2TokenValidator<Jwt> {

    override fun validate(token: Jwt?): OAuth2TokenValidatorResult {
        val response = tokenRepository.findTokenByUserId(token?.subject ?: "")
            .collectList()
            .map { it.map { userToken -> userToken.access }.contains(token?.tokenValue) }
            .map { if (it) OAuth2TokenValidatorResult.success() else OAuth2TokenValidatorResult.failure(OAuth2Error("1")) }
            .switchIfEmpty { Mono.just(OAuth2TokenValidatorResult.failure(OAuth2Error("1"))) }
            .toFuture()

        return response.get()
    }
}