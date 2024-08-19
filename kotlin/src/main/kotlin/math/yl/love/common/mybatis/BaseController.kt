package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/user")
abstract class BaseController<Entity: BaseEntity, Mapper: BaseMapper<Entity>, Service: BaseService<Entity, Mapper>> {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    protected lateinit var baseService: Service

    @PostMapping("create")
    @Operation(summary = "创建")
    fun create(@RequestBody value: Entity) = baseService.create(value)

    @PostMapping("update")
    @Operation(summary = "更新")
    fun update(@RequestBody value: Entity) = baseService.update(value)

    @PostMapping("delete/{id}")
    @Operation(summary = "根据id删除")
    fun delete(@PathVariable @Schema(description = "主键id")  id: Long) = baseService.delete(id)

    @PostMapping("get/{id}")
    @Operation(summary = "根据id获取")
    fun get(@PathVariable id: Long): Entity = baseService.getById(id)
}