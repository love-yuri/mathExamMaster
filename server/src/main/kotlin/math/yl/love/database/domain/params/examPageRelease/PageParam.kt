package math.yl.love.database.domain.params.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class PageParam (
    @Schema(description = "页码")
    val current: Long,

    @Schema(description = "每页条数")
    val size: Long,

    @Schema(description = "请求flag")
    val flag: Int = 0,
)