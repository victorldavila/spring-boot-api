package com.finapp.api.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors


data class UserDetailsImpl(
    val id: String?,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    @JsonIgnore val password: String,
    val authorities: MutableCollection<GrantedAuthority>
): UserDetails {
    override fun getAuthorities(): MutableCollection<GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        fun build(user: User): UserDetails =
            UserDetailsImpl(
                user.id?.toHexString(),
                user.firstName,
                user.lastName,
                user.username,
                user.email,
                user.password,
                user.roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()
            )
    }
}
