package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.user.LoginQuery
import math.yl.love.database.mapper.UserMapper
import math.yl.love.database.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
class UserController: BaseController<User, UserMapper, UserService>() {

    @PostMapping("login")
    @Operation(summary = "登陆")
    fun login(@RequestBody query: LoginQuery) = R.success(baseService.login(query))

    @PostMapping("info")
    @Operation(summary = "获取用户信息")
    fun getUserInfo() = R.success(baseService.getUserInfo())
}