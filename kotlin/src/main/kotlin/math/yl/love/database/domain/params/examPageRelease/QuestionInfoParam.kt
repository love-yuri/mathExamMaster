package math.yl.love.database.domain.params.examPageRelease

import com.fasterxml.jackson.databind.ser.std.EnumSerializer
import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.configuration.config.JsonConfig

@Serializable
data class QuestionInfoParam (
    @Schema(description = "练习id")
    val examPageId: Long,

    @Schema(description = "详情id")
    val relationId: Long,
)