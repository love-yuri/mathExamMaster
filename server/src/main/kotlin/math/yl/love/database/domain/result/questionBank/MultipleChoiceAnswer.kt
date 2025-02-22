package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

/**
 * 多选题json数据
 */
@Serializable
@SerialName(QuestionTypeEnum.MULTIPLE_CHOICE_SRT)
data class MultipleChoiceAnswer(
    val answer: List<Int>, // 正确答案列表，选项的顺序，从0开始
    val options: List<String> // 选项列表
): QuestionAnswer()