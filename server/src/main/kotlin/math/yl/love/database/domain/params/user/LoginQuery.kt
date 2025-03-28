package math.yl.love.database.domain.params.user

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

/**
 * 登陆接口请求 params
 */
@Serializable
data class LoginQuery(
    @Schema(description = "用户名") val username: String,
    @Schema(description = "密码") val password: String,
)