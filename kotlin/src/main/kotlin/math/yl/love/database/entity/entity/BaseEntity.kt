package math.yl.love.database.entity.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * 基础实体类
 * id 为自动增长
 * 创建时间-更新时间会自动注入
 */
@Serializable
open class BaseEntity(
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键id")
    val id: Long? = null,

    @TableLogic
    @TableField("deleted")
    @Schema(description = "是否被删除")
    val deleted: Boolean = false,

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    @Contextual
    val createdTime: LocalDateTime? = null,

    @TableField("created_by")
    @Schema(description = "创建用户")
    val createdBy: Long? = null,

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    @Contextual
    val updatedTime: LocalDateTime? = null,
)
