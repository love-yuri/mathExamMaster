package math.yl.love.database.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.common.utils.JwtUtils
import math.yl.love.configuration.auth.DetailUserInfo
import math.yl.love.database.entity.entity.User
import math.yl.love.database.entity.query.user.LoginQuery
import math.yl.love.database.entity.result.user.LoginJwtResult
import math.yl.love.database.entity.result.user.LoginResult
import math.yl.love.database.mapper.UserMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserService(
    private val authenticationManager: AuthenticationManager
) : BaseService<User, UserMapper>() {

    override val entityClass: KClass<User> get() = User::class

    override fun list(): List<User> {
        super.list().forEach {
            log.info("yuri: list -> ${it.toJson()}")
        }

        return super.list()
    }

    fun test2(): String {
        return R.success().toJson();
    }

    fun login(loginQuery: LoginQuery): LoginResult {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginQuery.username, loginQuery.password)
        val user = authenticationManager.authenticate(usernamePasswordAuthenticationToken).let {
            (it.principal as DetailUserInfo).user
        }

        val token = JwtUtils.createToken(LoginJwtResult(
            user.id!!,
            user.username!!,
            user.role
        ))
        return LoginResult(user, token)
    }

    /**
     * 根据用户名查询
     */
    fun getByUsername(username: String?): User? = one(queryWrapper.eq(!username.isNullOrEmpty(), User::username, username))

    /**
     * 查找用户信息
     */
    fun getUserInfo(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as LoginJwtResult
        return getByUsername(userDetails.username)!!
    }

    override fun create(entity: User): Boolean {
        log.info("yuri: create -> ${entity.toJson()}")
        return super.create(entity)
    }
}