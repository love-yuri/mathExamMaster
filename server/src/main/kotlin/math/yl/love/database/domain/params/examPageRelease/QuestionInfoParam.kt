package math.yl.love.database.domain.params.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class QuestionInfoParam (
    @Schema(description = "练习id")
    val examPageId: Long,

    @Schema(description = "详情id")
    val relationId: Long,
)