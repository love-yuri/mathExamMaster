package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


abstract class BaseController<Entity: BaseEntity, Mapper: BaseMapper<Entity>> {

    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    private lateinit var baseService: BaseService<Entity, Mapper>

    @PostMapping("create")
    fun create(@RequestBody value: Entity) = baseService.create(value)

    @PostMapping("update")
    fun update(@RequestBody value: Entity) = baseService.update(value)

    @PostMapping("delete/{id}")
    fun delete(@PathVariable id: Long) = baseService.delete(id)

    @PostMapping("get/{id}")
    fun get(@PathVariable id: Long): Entity = baseService.getById(id)
}