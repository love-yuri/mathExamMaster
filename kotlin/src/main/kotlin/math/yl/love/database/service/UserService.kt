package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseEntity
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.common.utils.JwtUtils
import math.yl.love.configuration.auth.DetailUserInfo
import math.yl.love.database.entity.entity.User
import math.yl.love.database.entity.query.user.LoginQuery
import math.yl.love.database.entity.result.user.LoginJwtResult
import math.yl.love.database.entity.result.user.LoginResult
import math.yl.love.database.mapper.UserMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val authenticationManager: AuthenticationManager
) : BaseService<User, UserMapper>() {

    fun test(): String {
        return list().joinToString {
            "id: ${it.id} info: ${it.toJson()}"
        }
    }

    fun test2(): String {
        return R.success().toJson();
    }

    fun login(loginQuery: LoginQuery): LoginResult {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginQuery.id, loginQuery.password)
        val user = authenticationManager.authenticate(usernamePasswordAuthenticationToken).let {
            (it.principal as DetailUserInfo).user
        }

        val token = JwtUtils.createToken(LoginJwtResult(
            user.id!!,
            user.role
        ))
        return LoginResult(user, token)
    }
}