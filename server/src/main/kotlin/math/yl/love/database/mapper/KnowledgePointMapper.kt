package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import math.yl.love.database.domain.entity.KnowledgePoint
import org.apache.ibatis.annotations.Mapper

@Mapper
interface KnowledgePointMapper: BaseMapper<KnowledgePoint> {
    fun selectPageVo(page: IPage<KnowledgePoint>, state: Int): List<KnowledgePoint>
}