package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.ExamPageQuestionRelation
import math.yl.love.database.domain.entity.ExamPageUserRelation
import math.yl.love.database.mapper.ExamPageQuestionRelationMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class ExamPageQuestionRelationService: BaseService<ExamPageQuestionRelation, ExamPageQuestionRelationMapper>() {

    /**
     * 根据试卷id查询
     */
    fun findByPageId(id: Long): List<ExamPageQuestionRelation> = list(queryWrapper.eq(ExamPageQuestionRelation::examPageId, id))

    /**
     * 根据试卷id删除数据
     */
    fun deleteByPageId(id: Long) = remove(queryWrapper.eq(ExamPageQuestionRelation::examPageId, id))

}