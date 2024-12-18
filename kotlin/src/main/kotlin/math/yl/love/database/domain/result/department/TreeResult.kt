package math.yl.love.database.domain.result.department

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class TreeResult (
    @Schema(description = "key")
    val key: String,

    @Schema(description = "标签")
    val label: String,

    @Schema(description = "孩子")
    var children: List<TreeResult>,
)