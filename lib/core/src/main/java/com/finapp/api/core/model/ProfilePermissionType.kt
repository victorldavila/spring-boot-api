package com.finapp.api.core.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class ProfilePermissionType(val profileName: String) {
    SUPER_ADMIN("super_admin"),
    ADMIN("admin"),
    USER("user"),

    NONE("none");

    fun getGrantedAuthority(): GrantedAuthority =
        SimpleGrantedAuthority(this.profileName)

    companion object {
        fun getPermissionsByRoleType(roleType: String?): List<PermissionType>? = when(roleType) {
            ADMIN.name -> PermissionType.getAdminPermissions()
            SUPER_ADMIN.name -> PermissionType.getSuperAdminPermissions()
            USER.name -> PermissionType.getUserPermissions()

            else -> null
        }
    }
}