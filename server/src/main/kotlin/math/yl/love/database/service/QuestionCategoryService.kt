package math.yl.love.database.service

import math.yl.love.common.mybatis.BaseService
import math.yl.love.database.domain.entity.QuestionCategory
import math.yl.love.database.mapper.QuestionCategoryMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true)
class QuestionCategoryService: BaseService<QuestionCategory, QuestionCategoryMapper>() 