package math.yl.love.controller

import math.yl.love.database.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController(
    private val userService: UserService
) {

    @GetMapping("hello")
    fun hello(): String {
        userService.test()
        return "hell world"
    }

}