package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.common.utils.JsonUtils
import math.yl.love.common.utils.JsonUtils.parseJson
import math.yl.love.configuration.exception.BizException
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.domain.result.questionBank.SingleChoiceAnswer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import math.yl.love.database.mapper.QuestionBankMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionBankService: BaseService<QuestionBank, QuestionBankMapper>() {
    override val entityClass: KClass<QuestionBank> get() = QuestionBank::class

    fun  getAnswer(questionBank: QuestionBank): Any {
        return when(questionBank.type) {
            QuestionTypeEnum.SINGLE_CHOICE -> questionBank.answer.parseJson<SingleChoiceAnswer>()
            QuestionTypeEnum.SHORT_ANSWER -> questionBank.answer.parseJson<String>()
            else -> throw BizException("未知题型...")
        }
    }
}