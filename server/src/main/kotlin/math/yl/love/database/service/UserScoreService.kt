package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.UserScore
import math.yl.love.database.mapper.UserScoreMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class UserScoreService: BaseService<UserScore, UserScoreMapper>() {
    override val entityClass: KClass<UserScore> get() = UserScore::class
}