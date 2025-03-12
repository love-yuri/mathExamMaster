package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
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
    protected val queryWrapper get() = KtQueryWrapper(getEntityClass())
    protected val updateWrapper get() = KtUpdateWrapper(getEntityClass())

    /**
     * 分页数据
     * @param current 当前页码
     * @param size 每页大小
     */
    fun page(current: Long, size: Long) = BasePage(page(Page(current, size)))

    @Transactional(rollbackFor = [Exception::class])
    fun deleteByIds(ids: Collection<*>) {
        if (ids.isNotEmpty()) {
            remove(queryWrapper.`in`(BaseEntity::id, ids))
        }
    }

    /**
     * 根据id集合查询
     */
    fun findByIds(ids: Collection<*>): List<Entity> {
        if (ids.isNotEmpty()) {
            return list(queryWrapper.`in`(BaseEntity::id, ids))
        }
        return emptyList()
    }

    /**
     * 直接返回list
     * @return list结果
     */
    fun KtQueryWrapper<Entity>.list(): List<Entity> = list(this)

    /**
     * 直接返回list
     * @return list结果
     */
    fun KtUpdateWrapper<Entity>.update(): Int = baseMapper.update(this)

    /**
     * 直接返回list
     * @return list结果
     */
    fun KtQueryWrapper<Entity>.selectOne(): Entity? = getOne(this.last("limit 1"))
}

