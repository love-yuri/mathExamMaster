package math.yl.love.database.mapper

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import math.yl.love.database.domain.entity.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper: BaseMapper<User> {
    fun findBy(): User {
        return selectOne(LambdaQueryWrapper<User>()
            .eq(User::id, 1))
    }

    /**
     * 获取所有没有设置班级的学生
     */
    @Select(("""
        select * from user where role = 3 and not exists(
            select 1 from user_department where user_id = user.id
        ) and deleted = false 
    """))
    fun getNoClassStudents(page: Page<User>): Page<User>
}