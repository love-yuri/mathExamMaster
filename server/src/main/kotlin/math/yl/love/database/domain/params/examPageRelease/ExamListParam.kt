package math.yl.love.database.domain.params.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class ExamListParam (
    @Schema(description = "获取模式: 0: 进行中 1： 已结束")
    val mode: Int,
)