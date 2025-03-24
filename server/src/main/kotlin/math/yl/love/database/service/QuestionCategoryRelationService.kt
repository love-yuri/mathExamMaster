package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.QuestionCategoryRelation
import math.yl.love.database.mapper.QuestionCategoryRelationMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionCategoryRelationService: BaseService<QuestionCategoryRelation, QuestionCategoryRelationMapper>() 