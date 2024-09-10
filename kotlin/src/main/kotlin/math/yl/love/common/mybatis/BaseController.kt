package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import math.yl.love.common.base.R
import math.yl.love.database.entity.entity.BaseEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

abstract class BaseController<Entity: BaseEntity, Mapper: BaseMapper<Entity>, Service: BaseService<Entity, Mapper>> {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    protected lateinit var baseService: Service

    @PostMapping("create")
    @Operation(summary = "创建")
    fun create(@RequestBody value: Entity) = R.success(baseService.create(value))

    @PostMapping("update")
    @Operation(summary = "更新")
    fun update(@RequestBody value: Entity) = R.success(baseService.update(value))

    @PostMapping("delete/{id}")
    @Operation(summary = "根据id删除")
    fun delete(@PathVariable @Schema(description = "主键id") id: Long) = R.success(baseService.delete(id))

    @PostMapping("get/{id}")
    @Operation(summary = "根据id获取")
    fun get(@PathVariable id: Long) = R.success(baseService.getById(id))
}