package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.QuestionCategory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface QuestionCategoryMapper: BaseMapper<QuestionCategory>