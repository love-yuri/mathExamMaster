package math.yl.love.database.domain.params.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class PageParam (
    @Schema(description = "页码")
    val current: Int,

    @Schema(description = "每页条数")
    val size: Int,
)