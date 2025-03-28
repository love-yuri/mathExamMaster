package math.yl.love.database.domain.entity

import com.baomidou.mybatisplus.annotation.*
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import math.yl.love.common.constant.DataBaseConstant
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.common.mybatis.typeHandler.QuestionAnswerTypeHandler
import math.yl.love.configuration.config.JsonConfig
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import java.time.LocalDateTime

@NoArg
@Serializable
@TableName("question_bank", autoResultMap = true)
data class QuestionBank (
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

    @Schema(description = "更新时间")
    @Serializable(with = JsonConfig.LocalDateTimeSerializer::class)
    @TableField(value = DataBaseConstant.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    override val updateTime: LocalDateTime? = null,

    @Schema(description = "更新用户")
    @TableField(value = DataBaseConstant.UPDATE_BY, fill = FieldFill.INSERT_UPDATE)
    override val updateBy: String? = null,

    @Schema(description = "题目类型")
    @TableField(value = "type")
    val type: QuestionTypeEnum,

    @Schema(description = "题目内容")
    @TableField(value = "content")
    val content: String,

    @Schema(description = "答案")
    @TableField(value = "answer", typeHandler = QuestionAnswerTypeHandler::class)
    val answer: QuestionAnswer,

    @Schema(description = "题目描述")
    @TableField(value = "description")
    val description: String? = null,

    @Schema(description = "难度: 默认1")
    @TableField(value = "difficulty")
    val difficulty: Int = 5,

) : BaseEntity
