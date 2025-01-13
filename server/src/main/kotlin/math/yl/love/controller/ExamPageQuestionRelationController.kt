package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.ExamPageQuestionRelation
import math.yl.love.database.mapper.ExamPageQuestionRelationMapper
import math.yl.love.database.service.ExamPageQuestionRelationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exam/page/question/relation")
@Tag(name = "试卷-题目关联表")
class ExamPageQuestionRelationController: BaseController<ExamPageQuestionRelation, ExamPageQuestionRelationMapper, ExamPageQuestionRelationService>()