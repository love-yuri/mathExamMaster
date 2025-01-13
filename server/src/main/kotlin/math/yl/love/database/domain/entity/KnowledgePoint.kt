package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("knowledge_point")
data class KnowledgePoint (
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

    @Schema(description = "知识点名称")
    @TableField(value = "name")
    val name: String,

    @Schema(description = "知识点描述")
    @TableField(value = "description")
    val description: String? = null,

    @Schema(description = "父级知识点: 0 代表根知识")
    @TableField(value = "parent")
    val parent: Long = 0,

) : BaseEntity
