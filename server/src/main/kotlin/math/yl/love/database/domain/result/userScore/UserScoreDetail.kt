package math.yl.love.database.domain.result.userScore

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.User

@Serializable
data class UserScoreDetail (
    @Schema(description = "题目id")
    val questionId: Long,

    @Polymorphic
    @Schema(description = "答案详情")
    val questionAnswer: Any,

    @Schema(description = "用户得分")
    val score: Int = 0,

    @Schema(description = "该题总分")
    val totalScore: Int = 0,
)