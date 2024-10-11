package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.BankAndPoint
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.params.questionBank.SaveQuestionBankParam
import math.yl.love.database.mapper.QuestionBankMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionBankService(
    private val bankAndPointService: BankAndPointService,
): BaseService<QuestionBank, QuestionBankMapper>() {
    override val entityClass: KClass<QuestionBank> get() = QuestionBank::class

    /**
     * 创建题目并关联知识点
     * @param param 需要题目列表和知识点列表
     */
    @Transactional(rollbackFor = [Exception::class])
    fun save(param: SaveQuestionBankParam): Boolean {
        save(param.questionBank)
        if (param.knowledgePointIds.isNotEmpty()) {
            val bankId = param.questionBank.id!!
            val values = param.knowledgePointIds.map {
                BankAndPoint(questionBankId = bankId, knowledgePointId = it.toLong())
            }
            bankAndPointService.saveBatch(values)
        }
        return true
    }
}