package math.yl.love.database.domain.result.userScore

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.result.examPageUserRelation.UserAnswer
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

@Serializable
data class UserScoreDetail (
    @Schema(description = "题目id")
    @Serializable(with = LongAsStringSerializer::class)
    val questionId: Long,

    @Schema(description = "问题类型")
    val type: QuestionTypeEnum,

    @Schema(description = "问题答案")
    val questionAnswer: QuestionAnswer,

    @Schema(description = "用户答案")
    val userAnswer: UserAnswer,

    @Schema(description = "用户得分")
    var score: Int = 0,

    @Schema(description = "该题总分")
    val totalScore: Int = 0,
)