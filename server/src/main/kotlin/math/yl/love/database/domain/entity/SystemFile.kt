package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.typeEnum.FileTypeEnum
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("system_file")
data class SystemFile (
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    override var id: Long? = null,

    @Transient
    @TableLogic
    @TableField(DataBaseConstant.DELETED)
    @Schema(description = "是否被删除")
    val deleted: Boolean = false,

    @Contextual
    @Schema(description = "创建时间")
    @TableField(value = DataBaseConstant.CREATE_TIME, fill = FieldFill.INSERT)
    override val createTime: LocalDateTime? = null,

    @Schema(description = "创建用户")
    @TableField(value = DataBaseConstant.CREATE_BY, fill = FieldFill.INSERT)
    override val createBy: String? = null,

    @Contextual
    @Schema(description = "更新时间")
    @TableField(value = DataBaseConstant.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    override val updateTime: LocalDateTime? = null,

    @Schema(description = "更新用户")
    @TableField(value = DataBaseConstant.UPDATE_BY, fill = FieldFill.INSERT_UPDATE)
    override val updateBy: String? = null,

    @Schema(description = "文件名")
    @TableField(value = "filename")
    val filename: String,

    @Schema(description = "文件md5值")
    @TableField(value = "md5")
    val md5: String,

    @Schema(description = "路径")
    @TableField(value = "path")
    val path: String,

    @Schema(description = "文件类型: 默认其他类型")
    @TableField(value = "type")
    val type: FileTypeEnum = FileTypeEnum.OTHER,

) : BaseEntity
