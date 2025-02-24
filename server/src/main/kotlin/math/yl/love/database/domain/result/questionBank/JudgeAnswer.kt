package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

/**
 * 判断题json数据
 */
@Serializable
@SerialName(QuestionTypeEnum.JUDGE_SRT)
data class JudgeAnswer(
    val answer: Boolean? = null // 正确答案，true表示正确，false表示错误
) : QuestionAnswer()