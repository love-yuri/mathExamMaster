package math.yl.love.database.entity.result.user

import io.swagger.v3.oas.annotations.media.Schema

data class LoginJwtResult (
    @Schema(description = "用户id")
    val id: Long,

    @Schema(description = "角色")
    val role: Int?
)