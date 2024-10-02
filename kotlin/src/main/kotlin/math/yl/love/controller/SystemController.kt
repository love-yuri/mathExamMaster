package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.Log.log
import math.yl.love.common.base.R
import math.yl.love.database.domain.params.system.GenerateParam
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
    fun tables(name: String) = R.success(systemService.getTables(name))
}