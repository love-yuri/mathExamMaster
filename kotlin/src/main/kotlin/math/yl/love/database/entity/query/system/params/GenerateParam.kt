package math.yl.love.database.entity.query.system.params

import kotlinx.serialization.Serializable

@Serializable
data class GenerateParam (
    val tableName: String,
    val dataBaseName: String,
)