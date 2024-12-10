package math.yl.love.database.service

import cn.dev33.satoken.stp.StpUtil
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.common.utils.JwtUtils
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.user.LoginQuery
import math.yl.love.database.domain.result.user.LoginJwtResult
import math.yl.love.database.domain.result.user.LoginResult
import math.yl.love.database.domain.result.user.StudentResult
import math.yl.love.database.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserService : BaseService<User, UserMapper>() {

    override val entityClass: KClass<User> get() = User::class

    fun login(loginQuery: LoginQuery): LoginResult {
        val user = getByUsername(loginQuery.username) ?: throw RuntimeException("用户不存在")
        check(user.password == loginQuery.password) { "密码错误" }
        StpUtil.login(user.username)
        return LoginResult(user, StpUtil.getLoginId().toString())
    }

    /**
     * 根据用户名查询
     */
    fun getByUsername(username: String?): User? = getOne(queryWrapper.eq(!username.isNullOrEmpty(), User::username, username))

    /**
     * 查找用户信息
     */
    fun getUserInfo() = getByUsername(StpUtil.getLoginId().toString())

    /**
     * 获取学生列表
     */
    fun students(): List<StudentResult> = list().map { StudentResult(it.id!!, it.username!!) }
}