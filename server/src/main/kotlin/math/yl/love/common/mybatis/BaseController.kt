package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import math.yl.love.common.base.R
import math.yl.love.database.domain.entity.BaseEntity
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
    open fun create(@RequestBody value: Entity) = R.success(baseService.save(value))

    @PostMapping("update")
    @Operation(summary = "更新")
    open fun update(@RequestBody value: Entity) = R.success(baseService.updateById(value))

    @PostMapping("delete/{id}")
    @Operation(summary = "根据id删除")
    open fun delete(@PathVariable @Schema(description = "主键id") id: String) = R.success(baseService.removeById(id))

    @PostMapping("get/{id}")
    @Operation(summary = "根据id获取")
    open fun get(@PathVariable id: Long) = R.success(baseService.getById(id))
}