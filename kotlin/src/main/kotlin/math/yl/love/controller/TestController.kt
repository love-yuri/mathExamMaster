package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
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

    @GetMapping("hello")
    fun hello(): String {
        userService.test()
        return "hell world"
    }
}