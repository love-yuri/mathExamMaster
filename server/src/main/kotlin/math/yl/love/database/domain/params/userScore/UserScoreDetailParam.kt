package math.yl.love.database.domain.params.userScore

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import math.yl.love.database.domain.entity.User

@Serializable
data class UserScoreDetailParam(
    @Schema(description = "用户信息")
    val user: User,

    @Schema(description = "token")
    val token: String,
)
