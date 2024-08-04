package math.yl.love.base.mybatis

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.Serializable


class BaseManager<Mapper : BaseMapper<Entity>, Entity> : ServiceImpl<Mapper, Entity>(), IService<Entity> {

    fun deleteById(id: Int): Boolean {
        return this.removeById(id)
    }
}