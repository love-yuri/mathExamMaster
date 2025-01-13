package math.yl.love.database.domain.result.examPage

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.common.base.NoArg
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum


@NoArg
@Serializable
data class QuestionInfoResult (
    @Schema(description = "题目类型")
    val type: QuestionTypeEnum,

    @Schema(description = "具体信息")
    val infos: List<QuestionInfo>
) {
    @Serializable
    data class QuestionInfo(
        @Schema(description = "主键id")
        @Serializable(with = LongAsStringSerializer::class)
        val id: Long? = null,

        @Schema(description = "题目内容")
        val content: String,

        @Schema(description = "题目选项")
        val options: List<String>,

        @Schema(description = "用户答案")
        val answer: List<String>,

        @Schema(description = "题目序号")
        val index: Int,

        @Schema(description = "是否已作答")
        val hasAnswer: Boolean,

        @Schema(description = "题目类型")
        val type: QuestionTypeEnum,
    )
}
