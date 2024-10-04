package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.entity.BaseEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Component
@Transactional(readOnly = true)
abstract class BaseService <Entity: BaseEntity, Mapper: BaseMapper<Entity>> {
    protected val log: Logger = LoggerFactory.getLogger(javaClass)
    protected abstract val entityClass: KClass<Entity>
    protected val queryWrapper get() = KtQueryWrapper(entityClass.java)

    /**
     * 基础Mapper类
     */
    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    protected lateinit var baseMapper: Mapper

    /**
     * 创建
     * @param entity 待创建的类
     */
    @Transactional(rollbackFor = [Exception::class])
    fun create(entity: Entity) = baseMapper.insert(entity) == 1

    /**
     * 更新
     * @param entity 待更新的类
     */
    @Transactional(rollbackFor = [Exception::class])
    fun update(entity: Entity) = baseMapper.updateById(entity) == 1

    /**
     * 删除
     * @param id 主键
     */
    @Transactional(rollbackFor = [Exception::class])
    fun delete(id: Long) = baseMapper.deleteById(id) == 1

    /**
     * 获取所有返回结果
     */
    fun list(): List<Entity> = baseMapper.selectList(QueryWrapper())

    /**
     * 通过id查询结果
     * 如果id为null则直接返回null
     * @param id 主键
     */
    fun getById(id: Long?): Entity? = id?.let { baseMapper.selectById(it) }

    /**
     * 将queryWrapper 的内容返回为list
     * @param queryWrapper 查询条件
     */
    fun list(queryWrapper: KtQueryWrapper<Entity>): List<Entity> = baseMapper.selectList(queryWrapper)

    /**
     * 将queryWrapper 的内容返回为单个结果
     * @param queryWrapper 查询条件
     */
    fun one(queryWrapper: KtQueryWrapper<Entity>): Entity? = baseMapper.selectOne(queryWrapper)
}

