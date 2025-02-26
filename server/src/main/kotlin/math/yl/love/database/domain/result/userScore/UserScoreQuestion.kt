package math.yl.love.database.domain.result.userScore

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.common.mybatis.typeHandler.QuestionAnswerTypeHandler
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

@NoArg
@Serializable
@TableName(autoResultMap = true)
data class UserScoreQuestion (
    @Schema(description = "题目id")
    @TableField("question_id")
    @Serializable(with = LongAsStringSerializer::class)
    val questionId: Long,

    @Schema(description = "答案")
    @TableField("question_answer", typeHandler = QuestionAnswerTypeHandler::class)
    val questionAnswer: QuestionAnswer,

    @Schema(description = "题目得分")
    @TableField("question_score")
    val questionScore: Int,

    @Schema(description = "题目类型")
    @TableField("type")
    val type: QuestionTypeEnum
)