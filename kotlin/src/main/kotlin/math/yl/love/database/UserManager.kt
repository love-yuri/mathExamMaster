package math.yl.love.database

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import math.yl.love.base.mybatis.BaseManager
import math.yl.love.base.mybatis.SuperManager
import math.yl.love.database.entity.User
import math.yl.love.database.mapper.UserMapper

class UserManager : SuperManager<UserMapper, User>()