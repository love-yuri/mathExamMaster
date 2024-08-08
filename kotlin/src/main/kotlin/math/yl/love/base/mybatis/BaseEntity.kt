package math.yl.love.base.mybatis

import com.baomidou.mybatisplus.annotation.*
import com.fasterxml.jackson.annotation.JsonFormat
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

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    val createdTime: LocalDateTime? = null

    @TableField("created_by")
    val createdBy: Long? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    val updatedTime: LocalDateTime? = null
}
