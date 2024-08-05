package math.yl.love.base.mybatis

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import math.yl.love.database.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory


abstract class BaseService <Entity: BaseEntity, Mapper: BaseMapper<Entity>> : ServiceImpl<Mapper, Entity>(), IService<Entity> {

    /**
     * 快捷lambda Query查询类
     */
    protected abstract val queryWrapper: KtQueryWrapper<Entity>

    /**
     * 日志类
     * 因为和本地的log冲突，所以这里改用logger
     */
    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 创建
     */
    fun create(entity: Entity) = baseMapper.insert(entity)

    /**
     * 更新
     */
    fun update(entity: Entity) = baseMapper.updateById(entity)

    /**
     * 删除
     */
    fun delete(id: Long) = baseMapper.deleteById(id)



}

