package math.yl.love.configuration.auth

import math.yl.love.database.service.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


/**
 * 登陆认证
 */
@Service
class LoginAuth(
     @Lazy private val userService: UserService
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.getById(username?.toLong()) ?: run {
            throw RuntimeException("不存在该用户: $username")
        }
        return DetailUserInfo(user)
    }
}