package math.yl.love.database.domain.params.questionBank

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.domain.entity.QuestionBank

@Serializable
data class SaveQuestionBankParam(
    @Schema(description = "题目")
    val questionBank: QuestionBank,

    @Schema(description = "知识点")
    val knowledgePointIds: List<String>
)