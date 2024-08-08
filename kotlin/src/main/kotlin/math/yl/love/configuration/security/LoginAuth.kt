package math.yl.love.configuration.security

import math.yl.love.base.Log.log
import math.yl.love.database.entity.DetailUserInfo
import math.yl.love.database.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LoginAuth(
    private val userService: UserService
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.getById(username?.toLong()) ?: run {
            throw RuntimeException("不存在该用户: $username")
        }
        return DetailUserInfo(user)
    }
}