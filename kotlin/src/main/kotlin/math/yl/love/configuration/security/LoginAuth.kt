package math.yl.love.configuration.security

import math.yl.love.base.Log.log
import math.yl.love.database.entity.AutoUserInfo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LoginAuth: UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        log.info("yuri: 获取用户名: $username")
//        if (username == "yuri") {
//            throw RuntimeException("密码错误")
//        }
        return AutoUserInfo()
    }
}