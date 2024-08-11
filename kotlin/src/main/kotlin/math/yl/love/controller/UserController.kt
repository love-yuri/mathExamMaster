package math.yl.love.controller

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.entity.entity.User
import math.yl.love.database.mapper.UserMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    val authenticationManager: AuthenticationManager
): BaseController<User, UserMapper>() {

    @ApiOperationSupport(author = "xiaoymin@foxmail.com")
    @PostMapping("/login")
    fun login(@RequestBody user: User): R<User?> {
        log.info("yuri: 获取的参数")
        return R.success(User())
    }
}