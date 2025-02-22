package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.common.base.NoArg
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.result.userScore.UserScoreDetail
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("user_score")
data class UserScore (
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

    @Schema(description = "试卷发布id")
    @TableField(value = "page_release_id")
    val pageReleaseId: Long,

    @Schema(description = "用户id")
    @TableField(value = "user_id")
    val userId: Long,

    @Schema(description = "用户得分")
    @TableField(value = "score")
    val score: Int = 0,

    @Schema(description = "试卷总分")
    @TableField(value = "total_score")
    val totalScore: Int = 100,

    @Schema(description = "用户得分详情")
    @TableField(value = "detail")
    val detail: List<UserScoreDetail>,

) : BaseEntity
