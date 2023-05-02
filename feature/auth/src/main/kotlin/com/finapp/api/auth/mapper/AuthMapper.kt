package com.finapp.api.auth.mapper

import com.finapp.api.auth.model.CredentialRequest
import com.finapp.api.auth.model.SignUpRequest
import com.finapp.api.core.model.ProfilePermissionType
import com.finapp.api.user_api.role.model.RoleResponse
import com.finapp.api.user_api.token.model.TokenResponse
import com.finapp.api.user_api.model.UserResponse
import com.finapp.api.user_api.credential.data.Credential
import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.token.data.Token
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

    private fun String.toRole() = Role(
        type = ProfilePermissionType.getPermissionByType(this).name,
        name = ProfilePermissionType.getPermissionByType(this).permissionName
    )

    private fun CredentialRequest.toCredential(): Credential = Credential(
        username = this.username,
        password = passwordEncoder.encode(password)
    )
}
