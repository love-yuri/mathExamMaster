package math.yl.love.database.domain.params.examPageRelease

import kotlinx.serialization.Serializable
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.Department
import java.time.LocalDateTime

@Serializable
data class ExamPageReleaseParam(
    @Schema(description = "发布id")
    val id: Long? = null,

    @Schema(description = "试卷id")
    val examPageId: Long,

    @Contextual
    @Schema(description = "开始时间")
    val startTime: LocalDateTime,

    @Contextual
    @Schema(description = "结束时间")
    val endTime: LocalDateTime,

    @Schema(description = "发布组织id")
    val departmentId: Long,
)