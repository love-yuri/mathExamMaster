package math.yl.love.database.domain.result.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.User

@Serializable
data class StudentResult (
    @Schema(description = "用户id")
    @Serializable(with = LongAsStringSerializer::class)
    val id: Long,

    @Schema(description = "用户名")
    val username: String
)