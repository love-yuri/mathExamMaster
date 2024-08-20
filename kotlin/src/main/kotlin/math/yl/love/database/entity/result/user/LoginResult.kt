package math.yl.love.database.entity.result.user

import io.swagger.v3.oas.annotations.media.Schema
import math.yl.love.database.entity.entity.User

data class LoginResult(
    @Schema(description = "用户信息")
    val user: User,

    @Schema(description = "token")
    val token: String,
)