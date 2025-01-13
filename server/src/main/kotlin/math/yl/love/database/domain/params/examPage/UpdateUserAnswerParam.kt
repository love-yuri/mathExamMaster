package math.yl.love.database.domain.params.examPage

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.result.questionBank.FullQuestionBank
import java.time.LocalDateTime

@Serializable
data class UpdateUserAnswerParam (
    @Schema(description = "发布id")
    val relationId: Long,

    @Schema(description = "用户答案")
    val answer: Map<Long, List<String>>
)