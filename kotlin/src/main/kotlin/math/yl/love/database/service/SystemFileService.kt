package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.SystemFile
import math.yl.love.database.mapper.SystemFileMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class SystemFileService: BaseService<SystemFile, SystemFileMapper>() {
    override val entityClass: KClass<SystemFile> get() = SystemFile::class

    /**
     * 根据md5 查找数据
     */
    fun findByMd5(md5: String) = list(queryWrapper.eq(SystemFile::md5, md5))
}