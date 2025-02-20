package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.UserScore
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserScoreMapper: BaseMapper<UserScore>