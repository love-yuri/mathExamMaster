package math.yl.love.configuration.auth

import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Serializable
class DetailUserInfo (
    val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.id.toString()
    }
}