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
    override var id: Long? = null,

    @Transient
    @TableLogic
    @TableField("deleted")
    @Schema(description = "是否被删除")
    override val deleted: Boolean = false,

    @Contextual
    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    override val createTime: LocalDateTime? = null,

    @Schema(description = "创建用户")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    override val createBy: String? = null,

    @Contextual
    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    override val updateTime: LocalDateTime? = null,

    @Schema(description = "更新")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    override val updateBy: String? = null,

    @TableField("role")
    @Schema(description = "用户角色")
    val role: Int? = null,

    @TableField("user_name")
    @Schema(description = "用户名")
    val username: String? = null,

    @Transient
    @TableField("pass_word")
    @Schema(description = "密码")
    val password: String? = null,
) : BaseEntity