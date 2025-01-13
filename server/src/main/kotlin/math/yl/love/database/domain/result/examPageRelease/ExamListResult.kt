package math.yl.love.database.domain.result.examPageRelease

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import java.time.LocalDateTime

@Serializable
data class ExamListResult (
    @Schema(description = "主键id")
    @Serializable(with = LongAsStringSerializer::class)
    val id: Long,

    @Schema(description = "试卷名称")
    val name: String,

    @Contextual
    @Schema(description = "创建时间")
    val createTime: LocalDateTime? = null,
)