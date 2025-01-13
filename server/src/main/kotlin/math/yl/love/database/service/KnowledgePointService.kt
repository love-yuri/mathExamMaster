package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils.toJson
import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.mapper.KnowledgePointMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class KnowledgePointService: BaseService<KnowledgePoint, KnowledgePointMapper>() {
    override val entityClass: KClass<KnowledgePoint> get() = KnowledgePoint::class

}