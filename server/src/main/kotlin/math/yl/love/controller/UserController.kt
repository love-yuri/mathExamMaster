package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.params.user.GetStudentEnum
import math.yl.love.database.domain.params.user.LoginQuery
import math.yl.love.database.domain.params.user.SetTeacherParam
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

    class PageParam: BaseController.PageParam() {
        val studentFlag: GetStudentEnum = GetStudentEnum.All
    }

    @PostMapping("login")
    @Operation(summary = "登陆")
    fun login(@RequestBody query: LoginQuery) = R.success(baseService.login(query))

    @PostMapping("logout")
    @Operation(summary = "退出登陆")
    fun logout() = R.success(baseService.logout())

    @PostMapping("info")
    @Operation(summary = "获取用户信息")
    fun getUserInfo() = R.success(baseService.getUserInfo())

    @PostMapping("page")
    @Operation(summary = "分页")
    fun pageSimple(@RequestBody param: PageParam) = R.success(baseService.page(param))

    @PostMapping("set/teacher")
    @Operation(summary = "为组织设置老师")
    fun setTeacher(@RequestBody param: SetTeacherParam) = R.success(baseService.setTeacher(param))

    @PostMapping("teachers")
    @Operation(summary = "获取所有老师")
    fun teachers(param: Int = 0) = R.success(baseService.teachers(param))
}