package math.yl.love.database.domain.result.userScore

import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.result.questionBank.QuestionAnswer

@Serializable
data class UserScoreQuestion (
    @Schema(description = "题目id")
    @TableField("question_id")
    val questionId: Long,

    @Schema(description = "答案")
    @TableField("question_answer")
    val questionAnswer: QuestionAnswer,

    @Schema(description = "题目得分")
    @TableField("question_score")
    val questionScore: Int
)