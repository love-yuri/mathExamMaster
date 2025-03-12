package math.yl.love.database.service

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
}