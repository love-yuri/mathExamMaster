package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.Serializable

/**
 * 多选题json数据
 */
@Serializable
data class MultipleChoiceAnswer(
    val answer: List<Int>, // 正确答案列表，选项的顺序，从0开始
    val keys: List<Key> // 选项列表
) {
    @Serializable
    data class Key(
        val value: String // 选项的值
    )
}