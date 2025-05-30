package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.ExamPageRelease
import math.yl.love.database.domain.params.examPageRelease.ExamListParam
import math.yl.love.database.domain.params.examPageRelease.ExamPageReleaseParam
import math.yl.love.database.mapper.ExamPageReleaseMapper
import math.yl.love.database.service.ExamPageReleaseService
import org.springframework.web.bind.annotation.*
import math.yl.love.database.domain.params.examPageRelease.PageParam

@RestController
@RequestMapping("/exam/page/release")
@Tag(name = "试卷-发布表")
class ExamPageReleaseController: BaseController<ExamPageRelease, ExamPageReleaseMapper, ExamPageReleaseService>() {

    @PostMapping("release")
    @Operation(summary = "发布试卷")
    fun release(@RequestBody param: ExamPageReleaseParam) = R.success(baseService.release(param))

    @PostMapping("release/update")
    @Operation(summary = "更新发布试卷")
    fun releaseUpdate(@RequestBody param: ExamPageReleaseParam) = R.success(baseService.releaseUpdate(param))

    @PostMapping("page/simple")
    @Operation(summary = "获取当前用户所有发布的试卷")
    fun pageSimple(@RequestBody param: PageParam) = R.success(baseService.pageSimple(param))

    @PostMapping("detail/{id}")
    @Operation(summary = "根据id获取")
    fun detail(@PathVariable id: Long) = R.success(baseService.detail(id))

    @PostMapping("student/detail/{id}")
    @Operation(summary = "学生详情")
    fun studentDetail(@PathVariable id: Long) = R.success(baseService.studentDetail(id))

    @PostMapping("delete/{id}")
    @Operation(summary = "根据id删除")
    override fun delete(@PathVariable @Schema(description = "主键id") id: String) = R.success(baseService.removeDetail(id))

    @PostMapping("exam/list")
    @Operation(summary = "获取练习列表")
    fun examList(@RequestBody param: ExamListParam) = R.success(baseService.examList(param))

    @PostMapping("start/exam")
    @Operation(summary = "开始考试")
    fun startExam(@RequestBody id:Long) = R.success(baseService.startExam(id))

    @PostMapping("exam/info")
    @Operation(summary = "考试信息")
    fun examInfo(@RequestBody id:Long) = R.success(baseService.examInfo(id))

    @PostMapping("check")
    @Operation(summary = "检查该用户的练习")
    fun check(@RequestBody id: Long) = R.success(baseService.check(id))
}