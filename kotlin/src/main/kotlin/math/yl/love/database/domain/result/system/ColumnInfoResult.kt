package math.yl.love.database.domain.result.system

import kotlinx.serialization.Serializable

@Serializable
data class ColumnInfoResult(
    val field: String,
    val type: String,
    val comment: String? = null,
    val tableComment: String? = null,
)