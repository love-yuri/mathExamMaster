package math.yl.love.database.entity.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

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