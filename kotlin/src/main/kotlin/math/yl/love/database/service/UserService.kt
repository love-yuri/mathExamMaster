package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import math.yl.love.common.mybatis.BaseEntity
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.configuration.auth.DetailUserInfo
import math.yl.love.database.entity.entity.User
import math.yl.love.database.entity.query.LoginQuery
import math.yl.love.database.mapper.UserMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val authenticationManager: AuthenticationManager
) : BaseService<User, UserMapper>() {

    override val queryWrapper: KtQueryWrapper<User> get() = KtQueryWrapper(User())

    fun test() {
        val all = list().joinToString {
            "id: ${it.id} info: $it"
        }

        logger.info("yuri: 获取结果: $all")
        logger.info("yuri: 结果 -> ${list(queryWrapper.eq(BaseEntity::id, 1))}")
    }

    fun login(loginQuery: LoginQuery): User? {
        logger.info("yuri: 参数$loginQuery")
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginQuery.id, loginQuery.password)
        val res = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        logger.info("yuri: res: -> ${(res.principal as DetailUserInfo).toJson()} ")
        return null
    }
}