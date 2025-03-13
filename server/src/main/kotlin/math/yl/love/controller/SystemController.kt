package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.database.domain.params.system.AiCreateQuestionParam
import math.yl.love.database.domain.params.system.GenerateParam
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import math.yl.love.database.service.SystemService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/system")
@Tag(name = "系统接口")
class SystemController(
    private val systemService: SystemService
) {
    @PostMapping("generate")
    @Operation(summary = "生成代码")
    fun generate(@RequestBody param: GenerateParam) = R.success(systemService.generate(param))

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    fun uploadFile(@RequestParam("file") file: MultipartFile) = R.success(systemService.uploadFile(file))

    @GetMapping("/databases")
    @Operation(summary = "获取所有数据库")
    fun databases() = R.success(systemService.getDataBaseList())

    @GetMapping("/tables")
    @Operation(summary = "获取数据库下所有表")
    fun tables(@RequestBody name: String) = R.success(systemService.getTables(name))

    @PostMapping("/ai/create/question")
    @Operation(summary = "ai生成题目")
    fun aiCreateQuestion(@RequestBody param: AiCreateQuestionParam) = R.success(systemService.aiCreateQuestion(param))

    @PostMapping("/ai/create/score")
    @Operation(summary = "ai评分")
    fun aiCreateScore(@RequestBody param: UserScoreDetail) = R.success(systemService.aiCreateScore(param))
}