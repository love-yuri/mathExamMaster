package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.common.base.NoArg
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.configuration.config.JsonConfig
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("exam_page")
data class ExamPage (
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

    @Schema(description = "试卷的难度，默认6，最高9, 难")
    @TableField(value = "difficulty")
    val difficulty: Int = 6,

    @Schema(description = "试卷限时（秒），默认7200秒（2小时）")
    @TableField(value = "limited_time")
    val limitedTime: Int = 7200,

    @Schema(description = "试卷科目，默认0: 高等数学")
    @TableField(value = "subject")
    val subject: Int = 0,

    @Schema(description = "试卷的标题")
    @TableField(value = "title")
    val title: String,

    @Schema(description = "试卷总分，默认100")
    @TableField(value = "total_score")
    val totalScore: Int = 100,

    @Schema(description = "试卷的类型，默认0: 普通试卷")
    @TableField(value = "type")
    val type: Int = 0,

) : BaseEntity
