package com.finapp.api.auth.mapper

import com.finapp.api.auth.model.CredentialRequest
import com.finapp.api.auth.model.SignUpRequest
import com.finapp.api.core.model.PermissionType
import com.finapp.api.core.model.ProfilePermissionType
import com.finapp.api.user_api.credential.data.Credential
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.role.data.RoleItem
import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthMapper(
    private val passwordEncoder: PasswordEncoder
) {
    fun signUpRequestToUser(signUpRequest: SignUpRequest) = User(
        email = signUpRequest.email,
        firstName = signUpRequest.firstName,
        lastName = signUpRequest.lastName,
        credential = signUpRequest.credential.toCredential()
    )

    fun getRoleByRoleType(userId: ObjectId?, roleType: String) =
        Role(
            userId = userId,
            isActive = true,
            roleItems = ProfilePermissionType.getPermissionsByRoleType(roleType)?.map { it.toRole() }
        )

    private fun PermissionType.toRole() = RoleItem(
        type = this.name,
        name = this.permissionName
    )

    private fun CredentialRequest.toCredential(): Credential = Credential(
        username = this.username,
        password = passwordEncoder.encode(password)
    )
}
