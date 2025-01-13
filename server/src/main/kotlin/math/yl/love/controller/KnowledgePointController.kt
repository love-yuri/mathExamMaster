package math.yl.love.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import math.yl.love.common.base.R
import math.yl.love.common.mybatis.BaseController
import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.mapper.KnowledgePointMapper
import math.yl.love.database.service.KnowledgePointService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/knowledge/point")
@Tag(name = "知识点")
class KnowledgePointController: BaseController<KnowledgePoint, KnowledgePointMapper, KnowledgePointService>() {
    @PostMapping("page")
    @Operation(summary = "分页")
    fun page(@RequestBody param: PageParam) = R.success(baseService.page(param.current, param.size))

    @PostMapping("list")
    @Operation(summary = "获取所有知识点")
    fun list() =  R.success(baseService.list())
}