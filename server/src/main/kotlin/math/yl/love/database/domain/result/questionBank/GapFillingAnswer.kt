package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum


/**
 * 填空题json数据
 */
@Serializable
@SerialName(QuestionTypeEnum.GAP_FILLING_SRT)
data class GapFillingAnswer(
    val answer: List<String> // 填空题答案列表
): QuestionAnswer()