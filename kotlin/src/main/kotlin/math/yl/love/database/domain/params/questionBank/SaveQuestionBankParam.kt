package math.yl.love.database.domain.params.questionBank

import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.domain.entity.QuestionBank

@Serializable
data class SaveQuestionBankParam(
    val questionBank: QuestionBank,
    val knowledgePointIds: List<String>
)