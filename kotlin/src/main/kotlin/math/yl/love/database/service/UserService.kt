package math.yl.love.database.service

import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.common.utils.JwtUtils
import math.yl.love.configuration.auth.DetailUserInfo
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.user.LoginQuery
import math.yl.love.database.domain.result.user.LoginJwtResult
import math.yl.love.database.domain.result.user.LoginResult
import math.yl.love.database.domain.result.user.StudentResult
import math.yl.love.database.mapper.UserMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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
            logger.info("yuri: list -> ${it.toJson()}")
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
    fun getByUsername(username: String?): User? = getOne(queryWrapper.eq(!username.isNullOrEmpty(), User::username, username))

    /**
     * 查找用户信息
     */
    fun getUserInfo(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as LoginJwtResult
        return getByUsername(userDetails.username)!!
    }

    /**
     * 获取学生列表
     */
    fun students(): List<StudentResult> = list().map { StudentResult(it.id!!, it.username!!) }
}