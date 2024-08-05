package math.yl.love.database.mapper

import math.yl.love.base.mybatis.BaseService
import math.yl.love.database.entity.User
import org.springframework.stereotype.Service

@Service
class UserService(

): BaseService<User, UserMapper>() {

    fun test() {
        val all = list().joinToString {
            "id: ${it.id} info: $it"
        }
        logger.info("yuri: 获取结果: $all")
    }
}