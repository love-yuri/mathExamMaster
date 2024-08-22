package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class BaseService <Entity: BaseEntity, Mapper: BaseMapper<Entity>> {
    protected val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * 基础Mapper类
     */
    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    protected lateinit var baseMapper: Mapper

    /**
     * 创建
     */
    fun create(entity: Entity) = baseMapper.insert(entity) == 1

    /**
     * 更新
     */
    fun update(entity: Entity) = baseMapper.updateById(entity) == 1

    /**
     * 删除
     */
    fun delete(id: Long) = baseMapper.deleteById(id) == 1

    /**
     * 获取所有返回结果
     */
    fun list(): List<Entity> = baseMapper.selectList(QueryWrapper())

    /**
     * 通过id查询结果
     * 如果id为null则直接返回null
     */
    fun getById(id: Long?): Entity? = id?.let { baseMapper.selectById(it) }
}

