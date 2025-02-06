package math.yl.love.configuration.mvc

import cn.dev33.satoken.stp.StpInterface
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserRoleList(
    private val userService: UserService
): StpInterface  {

    private val log = LoggerFactory.getLogger(UserRoleList::class.java)

    override fun getPermissionList(
        loginId: Any?,
        loginType: String?
    ): List<String>? {
        return null
    }

    override fun getRoleList(
        loginId: Any?,
        loginType: String?
    ): List<String>? {
        val user = userService.getUserInfo()
        return listOf(user.role.toString())
    }
}