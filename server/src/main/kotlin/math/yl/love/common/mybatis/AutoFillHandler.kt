package math.yl.love.common.mybatis

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import math.yl.love.common.utils.CommonUtils
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import java.util.function.Supplier

/**
 * 配置创建时间和更新时间自动填充
 */
@Component
class AutoFillHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
        
        this.strictInsertFill(metaObject, "createBy", String::class.java, CommonUtils.username)
        this.strictInsertFill(metaObject, "updateBy", String::class.java, CommonUtils.username)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime::class.java)
        this.strictInsertFill(metaObject, "updateBy", String::class.java, CommonUtils.username)
    }

    /**
     * 重写填充逻辑
     * 去除不为null不更新的逻辑
     */
    override fun strictFillStrategy(
        metaObject: MetaObject?,
        fieldName: String?,
        fieldVal: Supplier<*>?
    ): MetaObjectHandler {
        val obj = fieldVal!!.get()
        if (Objects.nonNull(obj)) {
            metaObject?.setValue(fieldName, obj)
        }
        return this
    }
}
