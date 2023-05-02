package com.finapp.api.user_api.mapper

import com.finapp.api.user_api.data.User
import com.finapp.api.user_api.model.UserRequest
import com.finapp.api.user_api.model.UserResponse
import com.finapp.api.user_api.role.data.Role
import com.finapp.api.user_api.role.model.RoleResponse
import com.finapp.api.user_api.token.data.Token
import com.finapp.api.user_api.token.model.TokenResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun userToUserResponse(user: User, hasTokenInfo: Boolean = false, hasRoleInfo: Boolean = false): UserResponse = UserResponse(
        id = user.id?.toHexString(),
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        token = if (hasTokenInfo) user.token.last().toTokenResponse() else null,
        roles = if (hasRoleInfo) user.roles.map { it.toRoleResponse() } else null
    )

    fun userRequestToUser(userRequest: UserRequest) = User(
        firstName = userRequest.firstName!!,
        lastName = userRequest.lastName!!,
        email = userRequest.email!!
    )

    fun userRequestToUser(user: User, userRequest: UserRequest) = user.copy(
        firstName = userRequest.firstName ?: user.firstName,
        lastName = userRequest.lastName ?: user.lastName,
        email = userRequest.email ?: user.email,
        credential = user.credential?.copy(username = userRequest.username ?: user.credential.username)
    )

    private fun Role.toRoleResponse() = RoleResponse (
        type = this.type,
        name = this.name
    )

    private fun Token.toTokenResponse(): TokenResponse = TokenResponse(
        accessToken = this.access,
        refreshToken = this.refresh
    )
}