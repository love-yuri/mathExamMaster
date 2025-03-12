package math.yl.love.database.domain.params.system

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.User
import math.yl.love.database.domain.typeEnum.QuestionTypeEnum
import java.time.LocalDateTime

@Serializable
data class AiCreateQuestionParam (
    @Schema(description = "需要生成的题目类型")
    val type: QuestionTypeEnum,

    @Schema(description = "用户描述")
    val description: String,
)