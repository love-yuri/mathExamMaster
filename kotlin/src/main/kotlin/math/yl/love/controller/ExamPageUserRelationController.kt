package math.yl.love.controller

import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.ExamPageUserRelation
import math.yl.love.database.mapper.ExamPageUserRelationMapper
import math.yl.love.database.service.ExamPageUserRelationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exam/page/user/relation")
@Tag(name = "试卷-学生关联表")
class ExamPageUserRelationController: BaseController<ExamPageUserRelation, ExamPageUserRelationMapper, ExamPageUserRelationService>()