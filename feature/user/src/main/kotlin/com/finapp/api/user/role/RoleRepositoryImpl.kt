package com.finapp.api.user.role

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.role.repository.RoleRepository
import com.finapp.api.user_api.role.data.Role

@Repository
class RoleRepositoryImpl(
    private val userRepository: UserRepository
): RoleRepository {

    override fun findRolesByUsername(username: String): Flux<Role> =
        userRepository.findUserByUsername(username)
            .map { it.roles }
            .flatMapIterable { it }

    override fun saveRole(user: User, role: Role): Mono<User> =
        userRepository.saveUser(user.copy(roles = user.roles.toMutableList().apply { add(role) }))

    override fun deleteRole(user: User, role: Role): Mono<User> =
        userRepository.saveUser(user.copy(roles = user.roles.toMutableList().apply { remove(role) }))
}