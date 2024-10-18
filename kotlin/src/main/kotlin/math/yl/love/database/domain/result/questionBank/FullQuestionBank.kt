package math.yl.love.database.domain.result.questionBank

import math.yl.love.database.domain.entity.KnowledgePoint
import math.yl.love.database.domain.entity.QuestionBank
import kotlinx.serialization.Serializable

@Serializable
data class FullQuestionBank(
    val questionBank: QuestionBank,
    val knowledgePoints: List<KnowledgePoint>,
)
