package com.finapp.api.core.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class ProfilePermissionType(val permissionName: String) {
    SUPER_ADMIN_READ("super_admin:read"),
    SUPER_ADMIN_WRITE("super_admin:write"),
    SUPER_ADMIN_UPDATE("super_admin:update"),
    SUPER_ADMIN_DELETE("super_admin:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    NONE("none");

    fun getGrantedAuthority(): GrantedAuthority =
        SimpleGrantedAuthority(this.permissionName)

    companion object {
        fun getPermissionByType(type: String?): ProfilePermissionType = when(type) {

            SUPER_ADMIN_READ.name -> SUPER_ADMIN_READ
            SUPER_ADMIN_WRITE.name -> SUPER_ADMIN_WRITE
            SUPER_ADMIN_UPDATE.name -> SUPER_ADMIN_UPDATE
            SUPER_ADMIN_DELETE.name -> SUPER_ADMIN_DELETE

            ADMIN_READ.name -> ADMIN_READ
            ADMIN_WRITE.name -> ADMIN_WRITE
            ADMIN_UPDATE.name -> ADMIN_UPDATE
            ADMIN_DELETE.name -> ADMIN_DELETE

            USER_READ.name -> USER_READ
            USER_WRITE.name -> USER_WRITE
            USER_UPDATE.name -> USER_UPDATE
            USER_DELETE.name -> USER_DELETE

            else -> NONE
        }
    }
}