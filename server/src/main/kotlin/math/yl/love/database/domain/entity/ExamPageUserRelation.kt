package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import math.yl.love.common.base.NoArg
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.mybatis.typeHandler.ListUserAnswerTypeHandler
import math.yl.love.common.mybatis.typeHandler.UserAnswerTypeHandler
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.typeEnum.ExamPageStatusEnum
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("exam_page_user_relation", autoResultMap = true)
data class ExamPageUserRelation (
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

    @Schema(description = "试卷发布id")
    @TableField(value = "page_release_id")
    val pageReleaseId: Long,

    @Schema(description = "用户id")
    @TableField(value = "user_id")
    @Serializable(with = LongAsStringSerializer::class)
    val userId: Long,

    @Schema(description = "当前试卷的状态，默认0: 未完成")
    @TableField(value = "status")
    val status: ExamPageStatusEnum = ExamPageStatusEnum.NOT_START,

    @TableField(value = "answer", typeHandler = ListUserAnswerTypeHandler::class)
    @Schema(description = "用户答案")
    val answer: List<UserAnswer>? = null,

    @Contextual
    @Schema(description = "开始练习时间")
    @TableField(value = "exam_start_time")
    val examStartTime: LocalDateTime? = null,
) : BaseEntity
