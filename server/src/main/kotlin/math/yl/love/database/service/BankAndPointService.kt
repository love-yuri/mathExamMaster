package math.yl.love.database.service

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.BankAndPoint
import math.yl.love.database.mapper.BankAndPointMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class BankAndPointService: BaseService<BankAndPoint, BankAndPointMapper>() {

    fun findByQuestionBankId(questionBankId: Long): List<BankAndPoint> {
        return list(queryWrapper.eq(BankAndPoint::questionBankId, questionBankId))
    }
    
    /**
     * 根据知识点ID列表删除关联
     * @param questionBankId 题目ID
     * @param ids 知识点ID列表
     * @return 是否删除成功
     */
    @Transactional(rollbackFor = [Exception::class])
    fun deleteByIds(questionBankId: Long, ids: Collection<String>): Boolean {
        if (ids.isEmpty()) return true
        val wrapper = LambdaQueryWrapper<BankAndPoint>()
            .eq(BankAndPoint::questionBankId, questionBankId)
            .`in`(BankAndPoint::knowledgePointId, ids.map { it.toLong() })
        return remove(wrapper)
    }
}