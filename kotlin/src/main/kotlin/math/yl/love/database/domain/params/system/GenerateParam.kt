package math.yl.love.database.domain.params.system

import kotlinx.serialization.Serializable

@Serializable
data class GenerateParam (
    val tableName: String,
    val dataBaseName: String = "zyl",
)