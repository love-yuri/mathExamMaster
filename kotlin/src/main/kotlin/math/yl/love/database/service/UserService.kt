package math.yl.love.database.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import math.yl.love.base.mybatis.BaseEntity
import math.yl.love.base.mybatis.BaseService
import math.yl.love.database.entity.User
import math.yl.love.database.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService : BaseService<User, UserMapper>() {

    override val queryWrapper: KtQueryWrapper<User> get() = KtQueryWrapper(User())

    fun test() {
        val all = list().joinToString {
            "id: ${it.id} info: $it"
        }

        logger.info("yuri: 获取结果: $all")
        logger.info("yuri: 结果 -> ${list(queryWrapper.eq(BaseEntity::id, 1))}")
    }
}