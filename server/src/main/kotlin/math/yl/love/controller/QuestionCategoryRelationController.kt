package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.QuestionCategoryRelation
import math.yl.love.database.mapper.QuestionCategoryRelationMapper
import math.yl.love.database.service.QuestionCategoryRelationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/question/category/relation")
@Tag(name = "题目-分类关联表")
class QuestionCategoryRelationController: BaseController<QuestionCategoryRelation, QuestionCategoryRelationMapper, QuestionCategoryRelationService>()