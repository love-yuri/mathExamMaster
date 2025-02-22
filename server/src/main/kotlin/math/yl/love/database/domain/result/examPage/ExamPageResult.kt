package math.yl.love.database.domain.result.examPage

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.params.examPage.ExamPageQuestion
import java.time.LocalDateTime

@Serializable
data class ExamPageResult (

    @Schema(description = "试卷 id")
    @Serializable(with = LongAsStringSerializer::class)
    val id: Long,

    @Contextual
    @Schema(description = "发布时间")
    val releaseTime: LocalDateTime,

    @Contextual
    @Schema(description = "截止时间")
    val deadline: LocalDateTime? = null,

    @Schema(description = "难度")
    val difficulty: Int,

    @Schema(description = "考试限制时间")
    val limitedTime: Int,

    @Schema(description = "关联题目")
    val questions: List<ExamPageQuestion>,

    @Schema(description = "科目")
    val subject: Int,

    @Schema(description = "标题")
    val title: String,

    @Schema(description = "总分")
    val totalScore: Int,

    @Schema(description = "类型")
    val type: Int,
)