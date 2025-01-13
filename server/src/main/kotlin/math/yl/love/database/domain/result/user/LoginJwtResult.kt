package math.yl.love.database.domain.result.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Serializable
data class LoginJwtResult (
    @Schema(description = "用户id")
    val id: Long,

    @Schema(description = "用户名")
    val username: String,

    @Schema(description = "角色")
    val role: Int? = null
)