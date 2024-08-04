package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper: BaseMapper<User>