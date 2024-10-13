package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import math.yl.love.database.domain.entity.BaseEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Component
@Transactional(readOnly = true)
abstract class BaseService <Entity: BaseEntity, Mapper: BaseMapper<Entity>>: IService<Entity>, ServiceImpl<Mapper, Entity>() {
    protected val logger: Logger = LoggerFactory.getLogger(javaClass)
    protected abstract val entityClass: KClass<Entity>
    protected val queryWrapper get() = KtQueryWrapper(entityClass.java)

    /**
     * 分页数据
     * @param current 当前页码
     * @param size 每页大小
     */
    fun page(current: Long, size: Long) =  BasePage(page(Page(current, size)))

}

