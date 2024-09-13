package math.yl.love.database.entity.result.system

import kotlinx.serialization.Serializable

@Serializable
data class ColumnInfo(
    val field: String,
    val type: String,
    val comment: String? = null,
    val tableComment: String? = null,
)