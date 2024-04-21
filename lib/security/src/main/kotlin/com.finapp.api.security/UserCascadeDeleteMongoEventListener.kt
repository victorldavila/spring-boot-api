package com.finapp.api.security

import com.finapp.api.role.repository.RoleRepository
import com.finapp.api.users.data.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component

@Component
class UserCascadeDeleteMongoEventListener(
): AbstractMongoEventListener<User>() {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    override fun onBeforeConvert(event: BeforeConvertEvent<User>) {
        val userId = event.source.id

        roleRepository.deleteRoleByUserId(userId)
            .subscribe()
    }
}