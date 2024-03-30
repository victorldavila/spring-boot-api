package com.finapp.api.user.token

import com.finapp.api.core.error.BadRequestError
import com.finapp.api.security.jwt.JwtHelper
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.role.repository.RoleRepository
import com.finapp.api.user_api.token.data.Token
import com.finapp.api.user_api.token.repository.TokenRepository
import com.finapp.api.user_api.token.repository.TokenService
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class TokenServiceImpl(
    private val jwtHelper: JwtHelper,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenRepository: TokenRepository
): TokenService {
    override fun generateNewToken(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { roleRepository.findRolesByUserId(it.id) }
            .map { listOf(it.roleItems ?: emptyList(), it.featureToggles ?: emptyList()) }
            .map { it.flatten() }
            .map { Pair(jwtHelper.generateToken(user, it), jwtHelper.generateToken(user, it, true)) }
            .map { Token(it.first, it.second) }
            .flatMap { tokenRepository.saveToken(user, it) }

    override fun generateNewTokenByRefreshToken(refreshToken: String): Mono<User> =
        Mono.just(refreshToken)
            .flatMap { validateToken(it) }
            .map { jwtHelper.getUserIdFromJwtToken(it) }
            .flatMap { getUserByUserId(it) }
            .flatMap { joinUserAndRefreshToken(it, refreshToken) }
            .flatMap { tokenRepository.deleteToken(it.first, it.second) }
            .flatMap { generateNewToken(it) }

    override fun deleteToken(accessToken: String): Mono<User> =
        Mono.just(accessToken)
            .flatMap { validateToken(it) }
            .map { jwtHelper.getUserIdFromJwtToken(it) }
            .flatMap { getUserByUserId(it) }
            .flatMap { joinUserAndAccessToken(it, accessToken)}
            .flatMap { tokenRepository.deleteToken(it.first, it.second) }

    private fun joinUserAndRefreshToken(user: User, refreshToken: String) =
        Mono.just(user)
            .zipWith(tokenRepository.findTokenByRefreshToken(user, refreshToken)) { userData, token ->
                Pair(userData, token)
            }
            .switchIfEmpty { Mono.error(BadRequestError("Token is not valid")) }

    private fun joinUserAndAccessToken(user: User, accessToken: String) =
        Mono.just(user)
            .zipWith(tokenRepository.findTokenByAccessToken(user, accessToken)) { userData, token ->
                Pair(userData, token)
            }
            .switchIfEmpty { Mono.error(BadRequestError("Token is not valid")) }

    private fun validateToken(token: String) =
        Mono.just(token)
            .filter { jwtHelper.validateJwtToken(it) }
            .switchIfEmpty { Mono.error(BadRequestError("Token is not valid")) }

    private fun getUserByUserId(userId: String): Mono<User> =
        userRepository.findUserById(ObjectId(userId))
            .switchIfEmpty { Mono.error(BadRequestError("username or password are invalid")) }
}