package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

/**
 * 单选题json数据
 */
@Serializable
@SerialName(QuestionTypeEnum.SINGLE_CHOICE_SRT)
data class SingleChoiceAnswer(
    val answer: Int, // 正确答案，选项的顺序，从0开始
    val options: List<String> // 选项列表
): QuestionAnswer()