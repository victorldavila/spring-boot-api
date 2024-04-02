package com.finapp.api.user.service

import com.finapp.api.core.error.NotFoundError
import com.finapp.api.user.mapper.UserMapper
import com.finapp.api.user.data.User
import com.finapp.api.user.model.UserArg
import com.finapp.api.user.model.UserParam
import com.finapp.api.user.model.UserRequest
import com.finapp.api.user.model.UserResponse
import com.finapp.api.user.repository.UserRepository
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
    override fun getUserById(userParam: UserParam): Mono<UserResponse> =
        findUserById(userParam.userId)
            .map { userMapper.userToUserResponse(it) }

    override fun getAllUsers(): Flux<UserResponse> =
        userRepository.findAllUsers()
            .map { userMapper.userToUserResponse(it) }

    override fun updateUser(userArg: UserArg): Mono<UserResponse> =
        findUserById(userArg.userParam?.userId)
            .map { userMapper.userRequestToUser(it, userArg.request) }
            .flatMap { userRepository.saveUser(it) }
            .map { userMapper.userToUserResponse(it) }

    override fun createUser(userRequest: UserRequest): Mono<UserResponse> =
        Mono.just(userRequest)
            .map { userMapper.userRequestToUser(it) }
            .flatMap { userRepository.saveUser(it) }
            .map { userMapper.userToUserResponse(it) }

    override fun deleteUser(userArg: UserArg): Mono<Boolean> =
        findUserById(userArg.userParam?.userId)
            .flatMap { userRepository.deleteUser(it) }
            .map { it.wasAcknowledged() }

    private fun findUserById(userId: String?): Mono<User> =
        Mono.justOrEmpty(userId)
            .flatMap { userRepository.findUserById(ObjectId(it)) }
            .switchIfEmpty { Mono.error(NotFoundError("user does not exists")) }

}