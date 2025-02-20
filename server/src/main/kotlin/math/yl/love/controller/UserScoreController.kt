package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.mapper.UserScoreMapper
import math.yl.love.database.service.UserScoreService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/score")
@Tag(name = "用户得分记录表")
class UserScoreController: BaseController<UserScore, UserScoreMapper, UserScoreService>()