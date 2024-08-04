package math.yl.love.database.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AutoUserInfo: UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return "{noop}yuri"
    }

    override fun getUsername(): String {
        return "yuri"
    }
}