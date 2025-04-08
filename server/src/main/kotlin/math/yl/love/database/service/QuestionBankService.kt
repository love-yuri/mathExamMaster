package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.common.mybatis.BasePage
import math.yl.love.common.mybatis.BaseService
import math.yl.love.configuration.config.SystemConfig
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.BankAndPoint
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.entity.QuestionCategoryRelation
import math.yl.love.database.domain.params.questionBank.SaveQuestionBankParam
import math.yl.love.database.domain.result.questionBank.FullQuestionBank
import math.yl.love.database.mapper.QuestionBankMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

@Service
@Transactional(readOnly = true)
class QuestionBankService(
    private val bankAndPointService: BankAndPointService,
    private val knowledgePointService: KnowledgePointService,
    private val redisService: RedisService,
    private val systemConfig: SystemConfig,
    private val questionCategoryRelationService: QuestionCategoryRelationService,
    private val questionCategoryService: QuestionCategoryService,
    private val examPageQuestionRelationService: ExamPageQuestionRelationService
): BaseService<QuestionBank, QuestionBankMapper>() {

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
        // 保存分类
        if (param.questionCategoryIds.isNotEmpty()) {
            val batch = param.questionCategoryIds.map {
                QuestionCategoryRelation(
                    questionBankId = param.questionBank.id!!,
                    categoryId = it
                )
            }
            questionCategoryRelationService.saveBatch(batch)
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

        // 更新知识点关联
        val oldPointIds = bankAndPointService.findByQuestionBankId(param.questionBank.id!!).map { it.knowledgePointId.toString() }
        val removeIds = oldPointIds.minus(param.knowledgePointIds.toSet())
        val addIds = param.knowledgePointIds.minus(oldPointIds.toSet())
        if (addIds.isNotEmpty()) {
            val values = addIds.map {
                BankAndPoint(questionBankId = param.questionBank.id!!, knowledgePointId = it.toLong())
            }
            bankAndPointService.saveBatch(values)
        }
        if (removeIds.isNotEmpty()) {
            bankAndPointService.deleteByIds(param.questionBank.id!!, removeIds)
        }
        
        // 更新分类关联
        val oldCategoryIds = questionCategoryRelationService.findByQuestionBankId(param.questionBank.id!!).map { it.categoryId }
        val removeCategoryIds = oldCategoryIds.minus(param.questionCategoryIds.toSet())
        val addCategoryIds = param.questionCategoryIds.minus(oldCategoryIds.toSet())
        if (addCategoryIds.isNotEmpty()) {
            val batch = addCategoryIds.map {
                QuestionCategoryRelation(
                    questionBankId = param.questionBank.id!!,
                    categoryId = it,
                )
            }
            questionCategoryRelationService.saveBatch(batch)
        }

        if (removeCategoryIds.isNotEmpty()) {
            questionCategoryRelationService.deleteByQuestionBankIdAndCategoryIds(param.questionBank.id!!, removeCategoryIds)
        }
        
        return true
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun removeById(id: Serializable?): Boolean {
        super.removeById(id)
        examPageQuestionRelationService.deleteByQuestionId(id.toString().toLong())
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
            val categoryIds = questionCategoryRelationService.findByQuestionBankId(bank.id!!).map {
                it.categoryId
            }
            records.add(
                FullQuestionBank(
                    questionBank = bank,
                    knowledgePoints = knowledgePointService.findByIds(knowledgePointIds),
                    categories = questionCategoryService.findByIds(categoryIds)
                )
            )
        }
        result.records = records
        result.total = value.total
        return BasePage(result)
    }

    fun detail(id: Long): FullQuestionBank {
        val questionBank = getById(id) ?: throw BizException("题目${id}不存在!!!")
        val knowledgePointIds = bankAndPointService.findByQuestionBankId(questionBank.id!!).map {
            it.knowledgePointId
        }

        val categoryIds = questionCategoryRelationService.findByQuestionBankId(questionBank.id!!).map {
            it.categoryId
        }

        return FullQuestionBank(questionBank, knowledgePointService.findByIds(knowledgePointIds), questionCategoryService.findByIds(categoryIds))
    }
}