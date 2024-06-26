package com.finapp.api.role.repository

import com.finapp.api.role.data.Role
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RoleRepository {
    fun findRolesByUserId(userId: ObjectId?): Mono<Role>
    fun saveRole(role: Role): Mono<Role>
    fun deleteRole(role: Role): Mono<DeleteResult>
    fun deleteRoleByUserId(userId: ObjectId?): Flux<Role>
    fun logicalDeleteRole(role: Role): Mono<Role>
}