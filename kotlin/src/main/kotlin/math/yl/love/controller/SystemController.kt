package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.database.entity.query.system.params.GenerateParam
import math.yl.love.database.service.SystemService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/system")
@Tag(name = "系统接口")
class SystemController(
    private val systemService: SystemService
) {
    @PostMapping("generate")
    fun generate(@RequestBody param: GenerateParam) = R.success(systemService.generate(param))
}