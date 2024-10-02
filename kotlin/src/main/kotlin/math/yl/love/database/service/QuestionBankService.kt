package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.QuestionBank
import math.yl.love.database.mapper.QuestionBankMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionBankService: BaseService<QuestionBank, QuestionBankMapper>() {
    override val entityClass: KClass<QuestionBank> get() = QuestionBank::class
}