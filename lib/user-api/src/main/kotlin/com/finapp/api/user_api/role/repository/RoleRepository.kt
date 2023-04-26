package com.finapp.api.user_api.role.repository

import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.role.data.Role
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RoleRepository {
    fun findRolesByUsername(username: String): Flux<Role>
    fun saveRole(user: User, role: Role): Mono<User>
    fun deleteRole(user: User, role: Role): Mono<User>
}