package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import math.yl.love.common.base.Log.log
import math.yl.love.database.entity.result.user.LoginJwtResult
import org.apache.ibatis.reflection.MetaObject
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 配置创建时间和更新时间自动填充
 */
@Component
class AutoFillHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        metaObject.setterNames.forEach { fieldName ->
            log.info("$fieldName = ${metaObject.getValue(fieldName)}")
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as LoginJwtResult
        this.strictInsertFill(metaObject, "createBy", String::class.java, userDetails.username)
        this.strictInsertFill(metaObject, "updateBy", String::class.java, userDetails.username)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime::class.java)
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as LoginJwtResult
        this.strictInsertFill(metaObject, "updateBy", String::class.java, userDetails.username)
    }
}
