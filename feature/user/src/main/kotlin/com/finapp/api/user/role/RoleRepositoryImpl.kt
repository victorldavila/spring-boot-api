package com.finapp.api.user.role

import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.repository.UserRepository
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.role.repository.RoleRepository
import com.finapp.api.user_api.role.data.RoleItem
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo

@Repository
class RoleRepositoryImpl(
    private val template: ReactiveMongoTemplate
): RoleRepository {

    override fun findRolesByUserId(userId: ObjectId?): Mono<Role> =
        template.findOne(
            Query(
                Criteria
                    .where(Role.USER_ID_FIELD)
                    .isEqualTo(userId)
                    .and(Role.IS_ACTIVE_FIELD)
                    .isEqualTo(true)
            ),
            Role::class.java
        )

    override fun saveRole(role: Role): Mono<Role> =
        template.save(role)

    override fun deleteRole(role: Role): Mono<DeleteResult> =
        template.remove(role)

    override fun logicalDeleteRole(role: Role): Mono<Role> =
        template.save(role.copy(isActive = false))
}