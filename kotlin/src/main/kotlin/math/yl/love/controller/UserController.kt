package math.yl.love.controller

import math.yl.love.base.mybatis.BaseController
import math.yl.love.database.entity.User
import math.yl.love.database.mapper.UserMapper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController: BaseController<User, UserMapper>() {
}