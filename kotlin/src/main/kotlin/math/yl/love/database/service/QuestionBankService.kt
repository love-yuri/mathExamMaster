package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.BankAndPoint
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.params.questionBank.SaveQuestionBankParam
import math.yl.love.database.domain.result.questionBank.FullQuestionBank
import math.yl.love.database.mapper.QuestionBankMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionBankService(
    private val bankAndPointService: BankAndPointService,
    private val knowledgePointService: KnowledgePointService,
): BaseService<QuestionBank, QuestionBankMapper>() {
    override val entityClass: KClass<QuestionBank> get() = QuestionBank::class

    /**
     * 创建题目并关联知识点
     * @param param 需要题目列表和知识点列表
     */
    @Transactional(rollbackFor = [Exception::class])
    fun saveSimple(param: SaveQuestionBankParam): Boolean {
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

    /**
     * 创建题目并关联知识点
     * @param param 需要题目列表和知识点列表
     */
    @Transactional(rollbackFor = [Exception::class])
    fun updateSimple(param: SaveQuestionBankParam): Boolean {
        // 更新题库信息
        updateById(param.questionBank)

        val oldPointIds = bankAndPointService.findByQuestionBankId(param.questionBank.id!!).map { it.id!!.toString() }
        val removeIds = oldPointIds.minus(param.knowledgePointIds.toSet())
        val addIds = param.knowledgePointIds.minus(oldPointIds.toSet())
        if (addIds.isNotEmpty()) {
            val values = addIds.map {
                BankAndPoint(questionBankId = param.questionBank.id!!, knowledgePointId = it.toLong())
            }
            bankAndPointService.saveBatch(values)
        }
        if (removeIds.isNotEmpty()) {
            bankAndPointService.deleteByIds(removeIds)
        }
        return true
    }

    fun pageSimple(current: Long, size: Long): BasePage<FullQuestionBank> {
        val value = page(Page(current, size))
        val result = Page<FullQuestionBank>(current, size)
        val records = mutableListOf<FullQuestionBank>()
        value.records.forEach { bank ->
            val knowledgePointIds = bankAndPointService.findByQuestionBankId(bank.id!!).map {
                it.knowledgePointId
            }
            records.add(FullQuestionBank(bank, knowledgePointService.findByIds(knowledgePointIds)))
        }
        result.records = records
        result.total = value.total
        return BasePage(result)
    }

    fun detail(id: String): FullQuestionBank {
        val questionBank = getById(id.toLong()) ?: throw BizException("题目不存在!!!")
        val knowledgePointIds = bankAndPointService.findByQuestionBankId(questionBank.id!!).map {
            it.knowledgePointId
        }

        return FullQuestionBank(questionBank, knowledgePointService.findByIds(knowledgePointIds))
    }
}