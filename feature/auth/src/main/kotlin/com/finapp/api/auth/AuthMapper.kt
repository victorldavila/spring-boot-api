package com.finapp.api.auth

import com.finapp.api.core.model.ProfilePermissionType
import com.finapp.api.user_api.RoleResponse
import com.finapp.api.user_api.UserResponse
import com.finapp.api.user_api.credential.data.Credential
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.role.data.Role
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
        credential = signUpRequest.credential.toCredential(),
        roles = signUpRequest.roles.map { it.toRole() }
    )

    fun userToUserResponse(user: User): UserResponse = UserResponse(
        id = user.id?.toHexString(),
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        roles = user.roles.map { it.toRoleResponse() }
    )

    private fun String.toRole() = Role(
        type = ProfilePermissionType.getPermissionByType(this).name,
        name = ProfilePermissionType.getPermissionByType(this).permissionName
    )

    private fun CredentialRequest.toCredential(): Credential = Credential(
        username = this.username,
        password = passwordEncoder.encode(password)
    )

    private fun Role.toRoleResponse() = RoleResponse (
        type = this.type,
        name = this.name
    )
}
