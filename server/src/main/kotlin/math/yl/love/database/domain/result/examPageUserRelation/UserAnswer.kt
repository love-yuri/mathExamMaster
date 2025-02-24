package math.yl.love.database.domain.result.examPageUserRelation

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.result.questionBank.QuestionAnswer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

/**
 * 判断题json数据
 */
@Serializable
data class UserAnswer (
    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "问题id")
    val questionId: Long,

    @Schema(description = "是否已作答")
    val hasAnswer: Boolean,

    @Schema(description = "答案")
    val questionAnswer: QuestionAnswer,
)