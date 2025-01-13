package math.yl.love.database.domain.params.examPage

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.result.questionBank.FullQuestionBank
import java.time.LocalDateTime

@Serializable
data class ExamPageQuestion(
    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "题库ID")
    val questionBankId: Long,

    @Schema(description = "分数")
    val score: Int,

    @Schema(description = "完整题目信息")
    var fullQuestionBank: FullQuestionBank? = null
)

@Serializable
data class ReleasePageParam (
    @Schema(description = "id")
    val id: Long? = null,

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