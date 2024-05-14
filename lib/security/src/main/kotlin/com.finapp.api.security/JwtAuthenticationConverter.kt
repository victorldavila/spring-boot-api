package com.finapp.api.security

import com.finapp.api.users.repository.UserRepository
import org.bson.types.ObjectId
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationConverter(
    private val userRepository: UserRepository
) : Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    override fun convert(jwt: Jwt): Mono<AbstractAuthenticationToken> {
        return Mono.just(jwt)
            .flatMap { userRepository.findUserById(ObjectId(it.subject)) }
            .map { UsernamePasswordAuthenticationToken(it, jwt, jwt.getClaim("scope")) }
    }
}