package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.Serializable


/**
 * 填空题json数据
 */
@Serializable
data class GapFillingAnswer(
    val answer: List<Gap> // 填空题答案列表
) {
    @Serializable
    data class Gap(
        val value: String // 答案
    )
}