package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.QuestionCategory
import math.yl.love.database.domain.params.BasePageParam
import math.yl.love.database.mapper.QuestionCategoryMapper
import math.yl.love.database.service.QuestionCategoryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question/category")
@Tag(name = "题目分类表")
class QuestionCategoryController: BaseController<QuestionCategory, QuestionCategoryMapper, QuestionCategoryService>() {

    @PostMapping("page")
    @Operation(summary = "分页")
    fun page(@RequestBody param: BasePageParam) = R.success(baseService.page(param.current, param.size))

    @PostMapping("list")
    @Operation(summary = "获取所有分类")
    fun list() =  R.success(baseService.list())
}