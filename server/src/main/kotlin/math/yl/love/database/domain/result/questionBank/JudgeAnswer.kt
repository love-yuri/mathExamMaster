package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.Serializable

/**
 * 判断题json数据
 */
@Serializable
data class JudgeAnswer(
    val answer: Boolean // 正确答案，true表示正确，false表示错误
)