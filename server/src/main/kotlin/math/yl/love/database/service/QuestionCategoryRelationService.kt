package math.yl.love.database.service

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.QuestionCategoryRelation
import math.yl.love.database.mapper.QuestionCategoryRelationMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionCategoryRelationService: BaseService<QuestionCategoryRelation, QuestionCategoryRelationMapper>() {
    
    /**
     * 根据题目ID查询分类关联信息
     * @param questionBankId 题目ID
     * @return 分类关联列表
     */
    fun findByQuestionBankId(questionBankId: Long): List<QuestionCategoryRelation> {
        return queryWrapper.eq(QuestionCategoryRelation::questionBankId, questionBankId).list()
    }
    
    /**
     * 根据题目ID和分类ID列表删除关联
     * @param questionBankId 题目ID
     * @param categoryIds 分类ID列表
     * @return 是否删除成功
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteByQuestionBankIdAndCategoryIds(questionBankId: Long, categoryIds: Collection<Long>): Boolean {
        if (categoryIds.isEmpty()){
            return true
        }
        val wrapper = queryWrapper
            .eq(QuestionCategoryRelation::questionBankId, questionBankId)
            .`in`(QuestionCategoryRelation::categoryId, categoryIds)
        return remove(wrapper)
    }
} 