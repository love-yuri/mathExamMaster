package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

/**
 * 主观题
 */
@Serializable
@SerialName(QuestionTypeEnum.SUBJECTIVE_SRT)
data class SubjectiveAnswer(
    val answer: String? = null
): QuestionAnswer()