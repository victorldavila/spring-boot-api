package com.finapp.api.security

import com.finapp.api.role.repository.RoleRepository
import com.finapp.api.users.data.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent
import org.springframework.stereotype.Component

@Component
class UserCascadeDeleteMongoEventListener: AbstractMongoEventListener<User>() {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    override fun onBeforeDelete(event: BeforeDeleteEvent<User>) {
        val userId = event.source.getObjectId("_id")

        roleRepository.deleteRoleByUserId(userId)
            .subscribe()
    }
}