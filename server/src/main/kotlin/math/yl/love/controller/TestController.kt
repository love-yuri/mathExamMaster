package math.yl.love.controller

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.domain.params.examPageRelease.QuestionInfoParam
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import math.yl.love.database.mapper.KnowledgePointMapper
import math.yl.love.database.service.ExamPageService
import math.yl.love.database.service.QuestionBankService
import math.yl.love.database.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/test")
@Tag(name = "测试")
class TestController(
    private val userService: UserService,
    private val knowledgePointMapper: KnowledgePointMapper,
    private val questionBankService: QuestionBankService,
    private val examPageService: ExamPageService
) {

    private val log = LoggerFactory.getLogger(TestController::class.java)

    @PostMapping("page/test")
    fun pageTest() {
        val page = Page<KnowledgePoint>().apply {
            current = 2
        }
        val queryWrapper = KtQueryWrapper<KnowledgePoint>(KnowledgePoint::class.java)
        val res = knowledgePointMapper.selectPage(page, queryWrapper)
        log.info("""
            size: ${res.size}
            total: ${res.total}
            current: ${res.current}
            records: ${res.records}
            gget ${res.records.size}
        """.trimIndent())
    }

    @PostMapping("answer/test")
    fun answerTest() {
        val list = questionBankService.list().filter { it.type == QuestionTypeEnum.SINGLE_CHOICE }
    }

    @PostMapping("question/info")
    @Operation(summary = "获取考试信息")
    fun questionInfo(@RequestBody param: QuestionInfoParam) = R.success(examPageService.questionInfo(param))

}