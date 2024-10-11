package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.params.questionBank.SaveQuestionBankParam
import math.yl.love.database.mapper.QuestionBankMapper
import math.yl.love.database.service.QuestionBankService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question/bank")
@Tag(name = "题库")
class QuestionBankController: BaseController<QuestionBank, QuestionBankMapper, QuestionBankService>() {

    @PostMapping("save")
    @Operation(summary = "保存题目")
    fun save(@RequestBody param: SaveQuestionBankParam) = R.success(baseService.save(param))


}