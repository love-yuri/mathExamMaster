package math.yl.love.database.domain.result.system

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.User
import java.time.LocalDateTime

@Serializable
data class AiCreateScoreResult (
    @Schema(description = "得分")
    val score: Int,

    @Schema(description = "得分详情")
    val detail: String,
)