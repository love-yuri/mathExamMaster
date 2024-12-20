package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.ExamPageUserRelation
import math.yl.love.database.domain.params.examPageRelease.ExamPageReleaseParam
import math.yl.love.database.mapper.ExamPageUserRelationMapper
import math.yl.love.database.service.ExamPageUserRelationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/exam/page/user/relation")
@Tag(name = "试卷-学生关联表")
class ExamPageUserRelationController: BaseController<ExamPageUserRelation, ExamPageUserRelationMapper, ExamPageUserRelationService>() {
    @PostMapping("relation")
    @Operation(summary = "获取发布详情")
    fun relation(@RequestBody releaseId: Long) = R.success(baseService.relation(releaseId))

}