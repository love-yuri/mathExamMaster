package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.database.domain.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper: BaseMapper<User> {
    fun findBy(): User {
        return selectOne(LambdaQueryWrapper<User>()
            .eq(User::id, 1))
    }
}