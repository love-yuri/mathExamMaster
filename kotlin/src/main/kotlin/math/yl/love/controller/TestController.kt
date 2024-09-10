package math.yl.love.controller

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.database.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
@Tag(name = "测试")
class TestController(
    private val userService: UserService
) {

    @GetMapping("hello2")
    fun hello2(): R<String> {
        return R.success(userService.test2())
    }
}