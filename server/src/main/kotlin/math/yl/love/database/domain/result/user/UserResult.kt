package math.yl.love.database.domain.result.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import math.yl.love.database.domain.entity.User
import java.time.LocalDateTime

@Serializable
data class UserResult (
    @Serializable(with = LongAsStringSerializer::class)
    @Schema(description = "用户id")
    val id: Long,

    @Schema(description = "用户名称")
    val username: String,
)