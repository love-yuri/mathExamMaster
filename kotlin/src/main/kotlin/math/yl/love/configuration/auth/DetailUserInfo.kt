package math.yl.love.configuration.security

import kotlinx.serialization.Serializable
import math.yl.love.database.entity.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Serializable
class DetailUserInfo (
    private val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return user.passWord!!
    }

    override fun getUsername(): String {
        return user.id.toString()
    }
}