package math.yl.love.database.domain.result.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import java.time.LocalDateTime

@Serializable
data class StartExamResult (
    @Schema(description = "试卷id")
    @Serializable(with = LongAsStringSerializer::class)
    val examPageId: Long,

    @Schema(description = "试卷名称")
    val name: String,

    @Contextual
    @Schema(description = "开始时间")
    val startTime: LocalDateTime? = null,

    @Contextual
    @Schema(description = "结束时间")
    val endTime: LocalDateTime
)