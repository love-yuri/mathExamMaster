package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 配置创建时间和更新时间自动填充
 */
@Component
class AutoFillHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime::now, LocalDateTime::class.java)
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime::class.java)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime::class.java)
    }
}
