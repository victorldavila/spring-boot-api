package com.finapp.api.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthUser(
    private val email: String,
    private val username: String,
    private val password: String,
    private val roles: List<String>
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
