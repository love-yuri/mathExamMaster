package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.BankAndPoint
import math.yl.love.database.mapper.BankAndPointMapper
import math.yl.love.database.service.BankAndPointService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bank/and/point")
@Tag(name = "知识点-题目关联表")
class BankAndPointController: BaseController<BankAndPoint, BankAndPointMapper, BankAndPointService>()