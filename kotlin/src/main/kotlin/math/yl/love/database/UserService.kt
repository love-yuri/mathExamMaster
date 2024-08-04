package math.yl.love.database

import math.yl.love.base.Log.log
import math.yl.love.base.mybatis.SuperService
import math.yl.love.database.entity.User
import math.yl.love.database.mapper.UserMapper
import org.springframework.stereotype.Service

@Service
class UserService: SuperService<User, UserMapper>() {
    fun test() {
        val all = baseManager.list().joinToString {
            "id: ${it.id} info: $it"
        }
        log.info("yuri: 获取结果: $all")
    }
}