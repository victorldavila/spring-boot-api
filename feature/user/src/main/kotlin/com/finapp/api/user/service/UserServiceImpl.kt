package com.finapp.api.user.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.user_api.mapper.UserMapper
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.model.UserRequest
import com.finapp.api.user_api.model.UserResponse
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.service.UserService
import org.bson.types.ObjectId
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
): UserService {
    override fun getUserById(userId: String): Mono<UserResponse> =
        findUserById(userId)
            .map { userMapper.userToUserResponse(it) }

    override fun getAllUsers(): Flux<UserResponse> =
        userRepository.findAllUsers()
            .map { userMapper.userToUserResponse(it) }

    override fun updateUser(userRequest: UserRequest): Mono<UserResponse> =
        findUserById(userRequest.id)
            .map { userMapper.userRequestToUser(it, userRequest) }
            .flatMap { userRepository.updateUser(it) }
            .map { userMapper.userToUserResponse(it) }

    override fun createUser(userRequest: UserRequest): Mono<UserResponse> =
        Mono.just(userRequest)
            .map { userMapper.userRequestToUser(it) }
            .flatMap { userRepository.saveUser(it) }
            .map { userMapper.userToUserResponse(it) }

    override fun deleteUser(userId: String): Mono<Boolean> =
        findUserById(userId)
            .flatMap { userRepository.deleteUser(it) }
            .map { it.wasAcknowledged() }

    private fun findUserById(userId: String): Mono<User> =
        userRepository.findUserById(ObjectId(userId))
            .switchIfEmpty { Mono.error(NotFoundError("user does not exists")) }

}