package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.mapper.QuestionBankMapper
import math.yl.love.database.service.QuestionBankService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question/bank")
@Tag(name = "题库")
class QuestionBankController: BaseController<QuestionBank, QuestionBankMapper, QuestionBankService>()