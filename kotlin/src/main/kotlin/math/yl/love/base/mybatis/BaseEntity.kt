package math.yl.love.base.mybatis

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import java.time.LocalDateTime

/**
 * 基础实体类
 * id 为自动增长
 * 创建时间-更新时间会自动注入
 */
open class BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    val id: Long? = null

    @TableLogic
    @TableField("deleted")
    val deleted: Boolean = false

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    val createdTime: LocalDateTime? = null

    @TableField("created_by")
    val createdBy: Long? = null

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    val updatedTime: LocalDateTime? = null
}
