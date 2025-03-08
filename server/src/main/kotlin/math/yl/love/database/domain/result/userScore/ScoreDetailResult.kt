package math.yl.love.database.domain.result.userScore

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum

@Serializable
data class ScoreDetailResult (
    @Schema(description = "学生人数")
    val totalStudent: Int,

    @Schema(description = "试卷总分")
    val totalScore: Int,

    @Schema(description = "90分以上的学生人数")
    val over90: Int,

    @Schema(description = "80分以上的学生人数")
    val over80: Int,

    @Schema(description = "70分以上的学生人数")
    val over70: Int,

    @Schema(description = "60分以上的学生人数")
    val over60: Int,

    @Schema(description = "60分以下的学生人数")
    val below60: Int,

    @Schema(description = "班级平均分")
    val averageScore: Double,

    @Schema(description = "班级中位分")
    val medianScore: Double,

    @Schema(description = "班级最高分")
    val maxScore: Int,

    @Schema(description = "班级最低分")
    val minScore: Int,

    @Schema(description = "及格率")
    val passRate: Double,

    @Schema(description = "每道题目的统计信息")
    val questionStatistics: List<QuestionStatistic>
) {
    @Serializable
    data class QuestionStatistic (
        @Schema(description = "正确人数")
        val index: Int,

        @Serializable(with = LongAsStringSerializer::class)
        @Schema(description = "题号")
        val questionId: Long,

        @Schema(description = "问题类型")
        val type: QuestionTypeEnum,

        @Schema(description = "正确人数")
        val correctCount: Int,

        @Schema(description = "班级最高分")
        val maxScore: Int,

        @Schema(description = "班级最低分")
        val minScore: Int,

        @Schema(description = "题目总分")
        val totalScore: Int,

        @Schema(description = "得分率")
        val scoreRate: Double,
    )
}


