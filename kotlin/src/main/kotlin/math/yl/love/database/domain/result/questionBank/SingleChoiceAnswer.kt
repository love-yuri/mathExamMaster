package math.yl.love.database.domain.result.questionBank

import kotlinx.serialization.Serializable

@Serializable
data class SingleChoiceAnswer (
    val answer: Int,
    val keys: List<Key>,
) {
    @Serializable
    data class Key(
        val value: String
    )
}
