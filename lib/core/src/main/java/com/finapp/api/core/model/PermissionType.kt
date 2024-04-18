package com.finapp.api.core.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class PermissionType(val permissionName: String) {
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

    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    NONE("none");

    fun getGrantedAuthority(): GrantedAuthority =
        SimpleGrantedAuthority(this.permissionName)

    companion object {
        fun getPermissionByType(type: String?): PermissionType = when(type) {

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

            PRODUCT_READ.name -> PRODUCT_READ
            PRODUCT_WRITE.name -> PRODUCT_WRITE
            PRODUCT_UPDATE.name -> PRODUCT_UPDATE
            PRODUCT_DELETE.name -> PRODUCT_DELETE

            else -> NONE
        }

        fun getPermissionByPermissionName(permissionName: String?): PermissionType = when(permissionName) {

            SUPER_ADMIN_READ.permissionName -> SUPER_ADMIN_READ
            SUPER_ADMIN_WRITE.permissionName -> SUPER_ADMIN_WRITE
            SUPER_ADMIN_UPDATE.permissionName -> SUPER_ADMIN_UPDATE
            SUPER_ADMIN_DELETE.permissionName -> SUPER_ADMIN_DELETE

            ADMIN_READ.permissionName -> ADMIN_READ
            ADMIN_WRITE.permissionName -> ADMIN_WRITE
            ADMIN_UPDATE.permissionName -> ADMIN_UPDATE
            ADMIN_DELETE.permissionName -> ADMIN_DELETE

            USER_READ.permissionName -> USER_READ
            USER_WRITE.permissionName -> USER_WRITE
            USER_UPDATE.permissionName -> USER_UPDATE
            USER_DELETE.permissionName -> USER_DELETE

            else -> NONE
        }

        fun getPermissions(): List<PermissionType> = listOf(
            SUPER_ADMIN_READ,
            SUPER_ADMIN_WRITE,
            SUPER_ADMIN_UPDATE,
            SUPER_ADMIN_DELETE,

            ADMIN_READ,
            ADMIN_WRITE,
            ADMIN_UPDATE,
            ADMIN_DELETE,

            USER_READ,
            USER_WRITE,
            USER_UPDATE,
            USER_DELETE,

            NONE
        )

        fun getAdminPermissions(): List<PermissionType> = listOf(
            ADMIN_READ,
            ADMIN_WRITE,
            ADMIN_UPDATE,
            ADMIN_DELETE
        )

        fun getSuperAdminPermissions(): List<PermissionType> = listOf(
            SUPER_ADMIN_READ,
            SUPER_ADMIN_WRITE,
            SUPER_ADMIN_UPDATE,
            SUPER_ADMIN_DELETE
        )

        fun getUserPermissions(): List<PermissionType> = listOf(
            USER_READ,
            USER_WRITE,
            USER_UPDATE,
            USER_DELETE
        )
    }
}