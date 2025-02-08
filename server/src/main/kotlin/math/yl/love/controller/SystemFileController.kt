package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.mapper.SystemFileMapper
import math.yl.love.database.service.SystemFileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/system/file")
@Tag(name = "文件列表")
class SystemFileController: BaseController<SystemFile, SystemFileMapper, SystemFileService>() {

    @GetMapping("/get/{fileId}")
    @Operation(summary = "直接访问文件")
    fun getFile(@PathVariable fileId: Long) = baseService.getFile(fileId)
}