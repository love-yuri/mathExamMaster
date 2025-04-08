package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.typeEnum.UserRoleEnum
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("user")
data class User (
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    override var id: Long? = null,

    @Transient
    @TableLogic
    @TableField(DataBaseConstant.DELETED)
    @Schema(description = "是否被删除")
    val deleted: Boolean = false,

    @Serializable(with = JsonConfig.LocalDateTimeSerializer::class)
    @Schema(description = "创建时间")
    @TableField(value = DataBaseConstant.CREATE_TIME, fill = FieldFill.INSERT)
    override val createTime: LocalDateTime? = null,

    @Schema(description = "创建用户")
    @TableField(value = DataBaseConstant.CREATE_BY, fill = FieldFill.INSERT)
    override val createBy: String? = null,

    @Serializable(with = JsonConfig.LocalDateTimeSerializer::class)
    @Schema(description = "更新时间")
    @TableField(value = DataBaseConstant.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    override val updateTime: LocalDateTime? = null,

    @Schema(description = "更新用户")
    @TableField(value = DataBaseConstant.UPDATE_BY, fill = FieldFill.INSERT_UPDATE)
    override val updateBy: String? = null,

    @TableField("role")
    @Schema(description = "用户角色")
    val role: UserRoleEnum = UserRoleEnum.STUDENT,

    @TableField("user_name")
    @Schema(description = "用户名")
    val username: String,

    @TableField("nick_name")
    @Schema(description = "昵称")
    val nickname: String,

    @TableField("pass_word")
    @Schema(description = "密码")
    val password: String,
) : BaseEntity