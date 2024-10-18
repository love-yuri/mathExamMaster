package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.params.questionBank.SaveQuestionBankParam
import math.yl.love.database.mapper.QuestionBankMapper
import math.yl.love.database.service.QuestionBankService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question/bank")
@Tag(name = "题库")
class QuestionBankController: BaseController<QuestionBank, QuestionBankMapper, QuestionBankService>() {

    @PostMapping("save/simple")
    @Operation(summary = "保存题目")
    fun saveSimple(@RequestBody param: SaveQuestionBankParam) = R.success(baseService.saveSimple(param))

    @PostMapping("update/simple")
    @Operation(summary = "更新题目")
    fun updateSimple(@RequestBody param: SaveQuestionBankParam) = R.success(baseService.updateSimple(param))

    @PostMapping("page")
    @Operation(summary = "分页")
    fun page(@RequestBody param: PageParam) = R.success(baseService.page(param.current, param.size))

    @PostMapping("page/simple")
    @Operation(summary = "分页")
    fun pageSimple(@RequestBody param: PageParam) = R.success(baseService.pageSimple(param.current, param.size))

    @PostMapping("detail/{id}")
    @Operation(summary = "获取详细结果")
    fun detail(@PathVariable id: String) = R.success(baseService.detail(id))
}