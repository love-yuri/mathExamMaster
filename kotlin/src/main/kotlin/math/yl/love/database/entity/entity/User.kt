package math.yl.love.database.entity.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDateTime

@TableName("user")
@Serializable
data class User (
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键id")
    override val id: Long? = null,

    @Transient
    @TableLogic
    @TableField("deleted")
    @Schema(description = "是否被删除")
    override val deleted: Boolean = false,

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    @Contextual
    override val createTime: LocalDateTime = LocalDateTime.now(),

    @TableField("create_by")
    @Schema(description = "创建用户")
    override val createBy: Long = 0,

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    @Contextual
    override val updateTime: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "用户角色")
    @TableField("role")
    val role: Int? = null,

    @TableField("user_name")
    @Schema(description = "用户名")
    val username: String? = null,

    @TableField("pass_word")
    @Transient
    @Schema(description = "密码")
    val password: String? = null,

) : BaseEntity